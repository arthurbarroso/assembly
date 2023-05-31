(ns revoir.translation-test
  (:require [revoir.core :as r]
            [clojure.test :refer [deftest testing is]]))

(def expected-result
  ["10" "2a" "ef" "fa" "48" "6a" "91" "b9" "c8"])

(def full-expected-result
  ["10" "00" "2a" "3a" "ef" "fa" "48" "58" "6a" "7a" "91" "81" "b9" "a9" "c8" "d8"])

(deftest parser-test
  (testing "Correctly parses the assembly code"
    (let [test-file-contents (r/read-file "resources/tests/base.asm")
          parsed-contents (r/parse test-file-contents)
          result (r/transform parsed-contents)]
      (is (= expected-result result))))
  (testing "Correctly parses the possible combinations"
    (let [test-file-contents (r/read-file "resources/tests/all.asm")
          parsed-contents (r/parse test-file-contents)
          result (r/transform parsed-contents)]
      (is (= full-expected-result result)))))


;; {:MOVE (constantly "000")
;;  :ADD (constantly "001")
;;  :SUB (constantly "010")
;;  :OR (constantly "011")
;;  :XOR (constantly "100")
;;  :SHIFT (constantly "101")
;;  :JMP (constantly "110")
;;  :GOTO (constantly "1110")
;;  :STORE (constantly "1111")
;;  :INSTRUCTION transform-instruction})
