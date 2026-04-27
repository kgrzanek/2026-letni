(ns fp.w4
  (:require
   [fp.w1 :as w1]))

;; make-rat <numerator> <denominator>) → ???
;; (numer ???) → numerator
;; (denom ???) → denominator

(defn gcd
  [m n]
  (let [r (mod m n)]
    (if (zero? r)
      n
      (recur n r))))

(gcd 4 24)

(defn make-rat
  [n d]
  (let [d1 (gcd n d)]
    (list (/ n d1) (/ d d1))))

(defn numer
  [x]
  (first x))

(defn denom
  [x]
  (first (next x)))

(make-rat 1 2)
(numer (make-rat 1 2))
(denom (make-rat 1 2))

(defn rat+ [x y]
  (make-rat (+ (* (numer x) (denom y))
               (* (numer y) (denom x)))
            (* (denom x) (denom y))))

(defn rat* [x y]
  (make-rat (* (numer x) (numer y))
            (* (denom x) (denom y))))

(rat+ (make-rat 1 2) (make-rat 6 2))

;; make-rat - KONSTRUKTOR
;; numer, denom - SELEKTORY

;; FORMA LET
;; (let [symbol1 wartość1
;;       symbol2 wartość2
;;       ...
;;       symbolN wartośćN]

;;   ciało)

(let [a 5
      b (+ a 4)]

  (println a b))

;; REPREZENTOWANIE PUNKTÓW PŁASZCZYZNY
(defn make-vect [x y] (list x y))       ;; konstruktor
(defn vect-x    [v]   (first v))        ;; selektor
(defn vect-y    [v]   (first (next v))) ;; selektor

;; ODCINKI NA PŁASZCZYŹNIE
(defn make-seg  [p q] (list p q))
(defn seg-start [s]   (first s))
(defn seg-end   [s]   (first (next s)))

(defn midpoint [s]
  (let [a (seg-start s)
        b (seg-end s)]
    (make-vect (w1/avg (vect-x a) (vect-x b))
               (w1/avg (vect-y a) (vect-y b)))))

;; i podobnie
(defn seg-length
  [s]
  (let [dx (- (vect-x (seg-end s))
              (vect-x (seg-start s)))
        dy (- (vect-y (seg-end s))
              (vect-y (seg-start s)))]
    (w1/sqrt 1 (+ (w1/square dx) (w1/square dy)))))

(def p1 (make-vect 3 4))
(def q1 (make-vect 5 7))
(def s1 (make-seg p1 q1))

(midpoint s1)
(double (seg-length s1))

(class (numer (rat+ (make-rat 1M 2M) (make-rat 6M 2M))))

(class (/ 7M 2M))
