(ns fp.w5)

(def x 3.14159)

(println x)
(+ x 5)

;; (println y)
(println (quote y))
(println "y")

;; Tato, a Marcin powiedział: "Warszawa jest stolicą USA"

(class "y")
(class (quote y))

(list (quote +) (quote x) 5)

(eval (list (quote +) (quote x) 5))

(eval (list '+ 'x 5))

(identity '(+ x 5))

;; RÓŻNICZKOWANIE SYMBOLICZNE
(defn atom?
  [expr]
  (not (seq? expr)))

(defn constant?
  [expr var]
  (and (atom? expr) (not (= expr var))))

(defn same-var?
  [expr var]
  (and (atom? expr) (= expr var)))

(defn sum?
  [expr]
  (and (seq? expr) (= (first expr) '+)))

#_(defn make-sum
    [lhs rhs]
    (list '+ lhs rhs))

(defn make-sum
  [lhs rhs]
  (cond (number? lhs)
        (cond (number? rhs)
              (+ lhs rhs)
              (zero? lhs)
              rhs
              :else (list '+ lhs rhs))
        (number? rhs)
        (cond (zero? rhs)
              lhs
              :else (list '+ lhs rhs))
        :else (list '+ lhs rhs)))

(defn product?
  [expr]
  (and (seq? expr) (= (first expr) '*)))

#_(defn make-product
    [lhs rhs]
    (list '* lhs rhs))

(defn make-product
  [lhs rhs]
  (cond (number? lhs)
        (cond (number? rhs)
              (* lhs rhs)
              (= 1 lhs)
              rhs
              (zero? lhs)
              0
              :else (list '* lhs rhs))
        (number? rhs)
        (cond (= 1 rhs)
              lhs
              (zero? rhs)
              0
              :else (list '* lhs rhs))
        :else (list '* lhs rhs)))

(defn L
  [expr]
  (second expr))

(defn R
  [expr]
  (nth expr 2))

(defn deriv
  [expr var]
  (cond (constant? expr var)
        0

        (same-var? expr var)
        1

        (sum? expr)
        (make-sum (deriv (L expr) var)
                  (deriv (R expr) var))

        (product? expr)
        (make-sum (make-product (L expr)
                                (deriv (R expr) var))
                  (make-product (deriv (L expr) var)
                                (R expr)))))

(def e1 '(+ (* a (* x x)) ;; ax^2 + bx + c
            (+ (* b x)
               c)))

(identity e1)

(deriv e1 'x)

(when-let [x nil]
  (* x 6))
