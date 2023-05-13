(ns revoir.parser-test
  (:require [revoir.core :as r]
            [clojure.test :refer [deftest testing is]]))

(def expected-result
  [[:INSTRUCTION [:OP [:MOVE "MOVE"]] [:OPERAND "0"] [:S "1"]]
   [:INSTRUCTION [:OP [:ADD "ADD"]] [:OPERAND "A"] [:S "0"]]
   [:INSTRUCTION [:SOP [:GOTO "GOTO"]] [:OPERAND "F"]]
   [:INSTRUCTION [:SOP [:STORE "STORE"]] [:OPERAND "A"]]
   [:INSTRUCTION [:OP [:SUB "SUB"]] [:OPERAND "8"] [:S "0"]]
   [:INSTRUCTION [:OP [:OR "OR"]] [:OPERAND "A"] [:S "0"]]
   [:INSTRUCTION [:OP [:XOR "XOR"]] [:OPERAND "1"] [:S "1"]]
   [:INSTRUCTION [:OP [:SHIFT "SHIFT"]] [:OPERAND "9"] [:S "1"]]
   [:INSTRUCTION [:OP [:JMP "JMP"]] [:OPERAND "8"] [:S "0"]]])

(deftest parser-test
  (testing "Correctly parses the assembly code"
    (let [test-file-contents (r/read-file "resources/tests/base.asm")
          result (r/parse test-file-contents)]
      (is (= expected-result result)))))
