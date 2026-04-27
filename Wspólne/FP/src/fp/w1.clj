(ns fp.w1)

(+ 1 2 3 4)
(reduce *' (range 1 5000))

;; WARTOŚCI PROSTE (ANG. PRIMITIVE VALUES)
(class  1.23) ;; java.lang.Double
(class     4) ;; java.lang.Long
(class "abc") ;; java.lang.String
(class  true) ;; java.lang.Boolean

(identity +)

;; ABSTRAKCJA
(def pi 3.14159)
(def kwadrat (fn [x] (* x x)))

(defn square [x] (* x x))

(kwadrat 3)
(map kwadrat (range 10))
(+ pi 3)

((fn [x] (* x x)) 5)

;; (<f> 5) => 25

;; WZÓR HERONA
(def e 0.0000001)

(defn good-enough?
  [G x]
  (< (abs (- (* G G) x)) e))

(defn avg [x1 x2]
  (/ (+ x1 x2) 2))

(defn improve ;; G → avg(G, x/G)
  [G x]
  (avg G (/ x G)))

(defn sqrt [G x]
  (if (good-enough? G x)
    G
    (sqrt (improve G x) x)))

(double (sqrt 1 2))
