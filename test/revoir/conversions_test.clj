(ns revoir.conversions-test
  (:require [revoir.conversions :as r]
            [clojure.test :refer [deftest testing is]]
            [clojure.string :as str]))

(def hex-list
  ["0" "1" "2" "3" "4" "5" "6" "7" "8" "9" "A" "B" "C" "D" "E" "F"])

(def binary-list
  ["0000"
   "0001"
   "0010"
   "0011"
   "0100"
   "0101"
   "0110"
   "0111"
   "1000"
   "1001"
   "1010"
   "1011"
   "1100"
   "1101"
   "1110"
   "1111"])

(deftest conversions-test
  (testing "Correctly matches hex to binary"
    (let [result (map r/hex->bin hex-list)]
      (is (= binary-list result))))
  (testing "Correctly matches binary to hex"
    (let [result (->> binary-list (map str/upper-case) (map r/bin->hex))]
      (is (= (map str/lower-case hex-list) result)))))
