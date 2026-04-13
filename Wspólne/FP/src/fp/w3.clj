(ns fp.w3)

(defn sum-ints
  [a b]
  (println "sum-ints" a b)
  (if (> a b)
    0
    (+ a
       (sum-ints (inc a) b))))

;; (sum-ints 1 5)
;; (+ 1 (sum-ints 2 5))
;; (+ 1 (+ 2 (sum-ints 3 5)))
;; (+ 1 (+ 2 (+ 3 (sum-ints 4 5))))
;; (+ 1 (+ 2 (+ 3 (+ 4 (sum-ints 5 5)))))
;; (+ 1 (+ 2 (+ 3 (+ 4 (+ 5 (sum-ints 6 5))))))
;; (+ 1 (+ 2 (+ 3 (+ 4 (+ 5 0)))))
;; (+ 1 (+ 2 (+ 3 (+ 4 5))))
;; (+ 1 (+ 2 (+ 3 9)))
;; (+ 1 (+ 2 12))
;; (+ 1 14)
;; 15

(defn square [x] (* x x))

(defn sum-squares
  [a b]
  (println "sum-squares" a b)
  (if (> a b)
    0
    (+ (square a)
       (sum-squares (inc a) b))))

(sum-squares 1 5)

;; (sum-squares 1 5)
;; (+ 1 (sum-squares 2 5))
;; (+ 1 (+ 4 (sum-squares 3 5)))
;; (+ 1 (+ 4 (+ 9 (sum-squares 4 5))))
;; (+ 1 (+ 4 (+ 9 (+ 16 (sum-squares 5 5)))))
;; (+ 1 (+ 4 (+ 9 (+ 16 (+ 25 (sum-squares 6 5))))))
;; (+ 1 (+ 4 (+ 9 (+ 16 (+ 25 0)))))
;; (+ 1 (+ 4 (+ 9 (+ 16 25))))
;; (+ 1 (+ 4 (+ 9 41)))
;; (+ 1 (+ 4 50))
;; (+ 1 54)
;; 55

(defn pi-sum
  [a b]
  (println "pi-sum" a b)
  (if (> a b)
    0
    (+ (/ 1 (* a (+ a 2)))
       (pi-sum (+ a 4) b))))

(pi-sum 1 50)

;; UOGÓLNIENIE
;; (defn <name>
;;   [a b]
;;   (if (> a b)
;;     0
;;     (+ (<term> a)
;;        (<name> (<next> a) b))))

(defn sum
  [a b term next]
  (if (> a b)
    0
    (+ (term a)
       (sum (next a) b term next))))

(sum 1 5 identity inc)
(sum 1 5 square   inc)

(sum 1 50
     (fn [a] (/ 1 (* a (+ a 2)))) ;; term
     (fn [a] (+ a 4))) ;; next

((fn [a] (+ a 4)) 7)

(defn inc-by
  [delta]
  (fn [a] (+ a delta)))

(inc-by 5)
(fn [a] (+ a 5))

((inc-by 4) 6)

(sum 1 50
     (fn [a] (/ 1 (* a (+ a 2)))) ;; term
     (inc-by 4)) ;; next

(defn pi-sum-term
  [a]
  (/ 1 (* a (+ a 2))))

(sum 1 50 pi-sum-term (inc-by 4))

(defn sum-iter
  ([a b term next]
   (sum-iter a b 0 term next))

  ([a b result term next]
   (if (> a b)
     result

     (recur (next a) b (+ result (term a)) term next)))) 0

(defn pi-sum-term-fast
  [a]
  (double (/ 1 (* a (+ a 2)))))

;; (time (sum-iter 1 50000000 pi-sum-term-fast (inc-by 4)))

;; HERON FIXED POINT
(defn FP-close-enough?
  [precision x y] (< (abs (- x y)) precision))

(defn FP-iter
  [f precision old current]
  (if (FP-close-enough? precision old current)
    current
    (recur f precision current (f current))))

(defn FIXED-POINT
  [f precision start]
  (FP-iter f precision start (f start)))

(defn avg
  [x y]
  (/ (+ x y) 2))

(defn sqrt
  [x]
  (FIXED-POINT (fn [y] (avg y (/ x y)))
               0.000001 ;; precision
               1))  ;; initial guess

(double (square (sqrt 2)))

;; TŁUMIENIE PRZEZ UŚREDNIANIE
(defn average-damp
  [f]
  (fn [x] (avg x (f x))))

(defn sqrt
  [x]
  (FIXED-POINT (average-damp (fn [y] (/ x y)))
               0.000001 ;; precision
               1)) ;; initial guess

(double (square (sqrt 2)))

;; METODA NEWTONA
(defn deriv
  [g dx]
  (fn [x]
    (/ (- (g (+ x dx)) (g x))
       dx)))

(defn sin [x] (Math/sin (double x)))

((deriv sin 0.000000001) 0)

(defn Newtons-transform
  [g dx]
  (fn [x]
    (- x (/ (g x)
            ((deriv g dx) x)))))

(defn Newtons-method
  [f dx precision y]
  (FIXED-POINT (Newtons-transform f dx) precision y))

(defn sqrt
  [x]
  (Newtons-method (fn [y] (- (square y) x)) ;; y → y^2 - x
                  0.0000001 ;; dx
                  0.00000000001 ;; precision
                  1))

(square (sqrt 2))

;; ELEMENTY PIERWSZORZĘDOWE JĘZYKA (ang. first-class objects/elements):
;; - Mogą być wartościami zmiennych (ogólnie - symboli)
;; - Mogą być argumentami procedur
;; - Mogą być wynikami procedur
;; - Mogą być elementami struktur danych

;; PROGRAMOWANIE FUNKCYJNE
;; RODZAJ DEKLARATYWNEGO STYLU PROGRAMOWANIA, W KTÓRYM OSIĄGA SIĘ
;; NIEZMIENNOŚĆ SYMBOLI POPRZEZ JEDNOCZESNE STOSOWANIE NASTĘPUJĄCYCH
;; PROGRAMISTYCZNYCH ŚRODKÓW WYRAZU:
;; I   FORMY IF (WYRAŻENIA WARUNKOWEGO)
;; II  REKURENCJI (W SENSIE DEFINICJI, W SENSIE PROCESOWYM NAJLEPIEJ GDYBY BYŁA KRAŃCOWA A PROCES INTERACYJNE)
;; III PROCEDUR (REALIZACJI FUNKCJI MATEMATYCZNYCH) JAKO ELEMENTÓW PIERWSZORZĘDOWYCH JĘZYKA
