(ns revoir.transformer-test
  (:require [revoir.core :as r]
            [clojure.test :refer [deftest testing is]]))

(def expected-result
  ["10" "2a" "ef" "fa" "48" "6a" "91" "b9" "c8"])

(deftest transformer-test
  (testing "Correctly transforms the assembly code"
    (let [test-file-contents (r/read-file "resources/tests/base.asm")
          tree (r/parse test-file-contents)
          result (r/transform tree)]
      (is (= expected-result result)))))
