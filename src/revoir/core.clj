(ns revoir.core
  (:require [clojure.string :as str]
            [clojure.java.io :as io]
            [instaparse.core :as insta]
            [revoir.conversions :as conversions]))

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

   OPERAND = #'\\d{1}|[A-Z]{1}'
   LINEEND = (NEWLINE | BLANKLINE)
   SPACE = ' '
   BLANKLINE = #'\\n\n'
   NEWLINE = #'\\n'
   COMMA = #'\\,'
   S = #'[0-1]'
  ")

(defn safe-extract [t]
  (if t
    (if (seq t)
      (last t)
      t)
    ""))

(defn- from-hex [s]
  (Integer/toBinaryString (Integer/parseInt s 16)))
  

(defn transform-instruction
  ([a]
   (transform-instruction a nil nil))
  ([a b]
   (transform-instruction a b nil))
  ([a b c]
   (let [a' (safe-extract a)
         b' (safe-extract b)
         c' (safe-extract c)
         f (str a' c' (conversions/hex->bin b'))]
     (conversions/b->h f))))

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

