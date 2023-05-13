(ns revoir.core
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [instaparse.core :as insta]))

(defn read-file [filename] (-> filename
                               io/file
                               slurp
                               (str/replace "\t" "")))

(def asm
  "<root> = (INSTRUCTION)+

   INSTRUCTION = (OP <SPACE> OPERAND <COMMA> <SPACE> S <LINEEND>?) |
                 SOP <SPACE> OPERAND <LINEEND>?
   OP = (ADD | MOVE | SUB | OR | XOR | SHIFT | JMP)
   SOP = (GOTO | STORE)

   ADD = #'(ADD)'
   MOVE = #'(MOVE)'
   SUB = #'(SUB)'
   OR = #'(OR)'
   XOR = #'(XOR)'
   SHIFT = #'(SHIFT)'
   JMP = #'(JMP)'
   GOTO = #'(GOTO)'
   STORE = #'(STORE)'

   OPERAND = #'[0-1]{4}'
   LINEEND = (NEWLINE | BLANKLINE)
   SPACE = ' '
   BLANKLINE = #'\\n\n'
   NEWLINE = #'\\n'
   COMMA = #'\\,'
   S = #'[0-1]'
  ")

(defn safe-extract [t]
  (if t
    (last t)
    ""))

(defn transform-instruction
  ([a]
   (transform-instruction a nil nil))
  ([a b]
   (transform-instruction a b nil))
  ([a b c]
   (let [f (str (safe-extract a)
                (safe-extract c)
                (safe-extract b))]
     (Integer/toString (Integer/parseInt f 2) 16))))

(def parser (insta/parser asm))

(defn parse [contents]
  (let [parsed-content (parser contents)]
    (if (insta/failure? parsed-content)
      (throw (ex-info "Failure parsing contents" {:contents parsed-content}))
      parsed-content)))

(def transforms
  {:MOVE (constantly "000")
   :ADD (constantly "001")
   :SUB (constantly "010")
   :OR (constantly "011")
   :XOR (constantly "100")
   :SHIFT (constantly "101")
   :JMP (constantly "110")
   :GOTO (constantly "1110")
   :STORE (constantly "1111")
   :INSTRUCTION transform-instruction})

(defn transform [tree]
  (insta/transform transforms tree))

(defn gen-output [contents]
  (str "v2.0 raw\n"
       (str/join "\n" contents)))

(defn write-file [contents filename]
  (spit filename (gen-output contents)))

(comment
  (-> "thing.asm"
      read-file
      parse
      transform
      (write-file "outputmemory")))
