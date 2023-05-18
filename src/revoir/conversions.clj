(ns revoir.conversions)

(def hex->bin-table
  {"0" "0000"
   "1" "0001"
   "2" "0010"
   "3" "0011"
   "4" "0100"
   "5" "0101"
   "6" "0110"
   "7" "0111"
   "8" "1000"
   "9" "1001"
   "A" "1010"
   "B" "1011"
   "C" "1100"
   "D" "1101"
   "E" "1110"
   "F" "1111"})

(def bin->hex-table
  {"0000" "0"
   "0001" "1"
   "0010" "2"
   "0011" "3"
   "0100" "4"
   "0101" "5"
   "0110" "6"
   "0111" "7"
   "1000" "8"
   "1001" "9"
   "1010" "a"
   "1011" "b"
   "1100" "c"
   "1101" "d"
   "1110" "e"
   "1111" "f"})

(defn hex->bin [hex]
  (let [bin (get hex->bin-table hex)]
    (if bin
      bin
      "0000")))

(defn bin->hex [bin]
  (let [hex (get bin->hex-table bin)]
    (if hex
      hex
      "0")))

(defn b->h [b]
  (let [p1 (subs b 0 4)
        p2 (subs b 4 8)]
    (str (bin->hex p1) (bin->hex p2))))

(comment
  (subs "01100000" 0 4)
  (subs "01100000" 4 9))
