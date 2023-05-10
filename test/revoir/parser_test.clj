(ns revoir.parser-test
  (:require [revoir.core :as r]
            [clojure.test :refer [deftest testing is]]))

(def expected-result
  [[:INSTRUCTION [:OP [:MOVE "MOVE"]] [:OPERAND "0000"] [:S "1"]]
   [:INSTRUCTION [:OP [:ADD "ADD"]] [:OPERAND "1010"] [:S "0"]]
   [:INSTRUCTION [:SOP [:GOTO "GOTO"]] [:OPERAND "1111"]]
   [:INSTRUCTION [:SOP [:STORE "STORE"]] [:OPERAND "1010"]]
   [:INSTRUCTION [:OP [:SUB "SUB"]] [:OPERAND "1000"] [:S "0"]]
   [:INSTRUCTION [:OP [:OR "OR"]] [:OPERAND "1010"] [:S "0"]]
   [:INSTRUCTION [:OP [:XOR "XOR"]] [:OPERAND "0001"] [:S "1"]]
   [:INSTRUCTION [:OP [:SHIFT "SHIFT"]] [:OPERAND "1001"] [:S "1"]]
   [:INSTRUCTION [:OP [:JMP "JMP"]] [:OPERAND "1000"] [:S "0"]]])

(deftest parser-test
  (testing "Correctly parses the assembly code"
    (let [test-file-contents (r/read-file "tests/base.asm")
          result (r/parse test-file-contents)]
      (is (= expected-result result)))))
