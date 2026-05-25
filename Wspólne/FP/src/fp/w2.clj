(ns fp.w2)

(defn square [x]
  (* x x))

(defn sum-of-squares [x y]
  (+ (square x) (square y)))

;; EWALUACJA - wyznaczenie wartości (wyrażenia)
;; WYRAŻENIE (ang. expression) - forma posiadająca wartość

;; Jak przebiega ewaluacja poniższego wyrażenia?
(sum-of-squares 3 4)
(+ (square 3) (square 4))
(+ (* 3 3) (* 4 4))
(+ 9 16)
25

(identity sum-of-squares)
(class 3)
(identity square)

;; EWALUCJA IF W MODELU PODSTAWIENIOWYM
;; (if <predykat>
;;   <następnik>
;;   <alternatywa>)

;; W Javie/C++ mamy tzw. ternary-conditional (trójargumentowe wyrażenie warunkowe)
;; e.g. var s = 4 > 5 ? "xyz" : "abc";

(if (> 4 5) ;; wyrażenie o wartości "abc"
  "xyz"
  "abc")

(defn my-if [pred consequent alternative]
  (cond pred
        consequent

        :else
        alternative))

(my-if (> 4 5) "xyz" "abc")

(def n 0)

(if (zero? n)
  :nie-dziel-przez-0

  (/ 1 n))

#_(my-if (zero? n)
         :nie-dziel-przez-0

         (/ 1 n))

(defn p+ [x y]
  (if (= x 0)
    y
    (recur #_p+ (dec x) (inc y))))

(p+ 3 4) ;; Jak przebiega ewaluacja tego wyrażenia?

(if (= 3 0)
    4
    (p+ (dec 3) (inc 4)))

(p+ (dec 3) (inc 4))
(p+ 2 5)

(if (= 2 0)
    5
    (p+ (dec 2) (inc 5)))

(p+ (dec 2) (inc 5))
(p+ 1 6)

(if (= 1 0)
    6
    (p+ (dec 1) (inc 6)))

(p+ 0 7)
(if (= 0 0)
    7
    (p+ (dec 0) (inc 7)))

7

;;  x y - zmienne stanu (ang. state-variables)
(p+ 3 4)
(p+ 2 5)
(p+ 1 6)
(p+ 0 7)
7

(p+ 10000000 4)

;; Q: Od czego zależy czas wykonania p+?
;; A: Od wielkości x
;; Q: Dlaczego?
;; A: Bo warunek zatrzymania algorytmu jest na `x`: (= x 0)

;; Q: Od czego zależy ilość pamięci konsumowanej przez obliczenie?
;; A: Jest ona stała

(defn r+ [x y]
  (if (= x 0)
    y
    (inc (r+ (dec x) y))))

;;  x y
(r+ 3 4)
(inc (r+ 2 4))
(inc (inc (r+ 1 4)))
(inc (inc (inc (r+ 0 4))))
(inc (inc (inc 4)))
(inc (inc 5))
(inc 6)
7

(defn silnia [n wartość]
  (if (= n 0)
    wartość
    (recur (dec n) (*' wartość n))))

;; (defn silnia [n]
;;   (if (= n 0)
;;     1
;;     (*' n (silnia (dec n)))))

;; (count (str (silnia 10000 1)))

(time (silnia 10000 1))

(defn fib [n]
  (if (< n 2)
    n
    (+' (fib (- n 1))
        (fib (- n 2)))))

(def fib (memoize fib))

(fib 300)

(defn factorial [n]
  (reduce *' (range 1 (inc n))))

(factorial 20000)

(time (last (take 4200 (map first (iterate (fn [[a b]] [b (+' a b)])
                                    [0 1])))))

(->> [0 1]
     (iterate (fn [[a b]] [b (+' a b)]))
     (map first)
     (take 100)
     last)
