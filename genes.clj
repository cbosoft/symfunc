;; GENES.clj

;; GENE: pair of function and number of free parameters it accepts. That is,
;; parameters on top of the main flow of data, parameters which can be mutated
;; as part of the optimisation.

;; GENOME: a list of genes

(defrecord Gene [function name])
(def genome [
             (->Gene + "add")
             (->Gene - "subtract")
             (->Gene * "multiply")
             (->Gene / "divide")
             ])

;;(println ((:function (first genome)) 1 2))

;;;; ;; ORGANISM: a collection of genes working together
(def an-organism '((get genome 1)))

(defn try-organism [organism input]
  ;; function to run organism on input
  (cond ((record? organism) (println "is record"))
        (1 (println "else"))))

(try-organism (get genome 1) 8)

;;;; (print "here")
;;;; 
;;;; (defn eval-organism [organism x]
;;;;   (defn iter [l xi]
;;;;     (print l)
;;;;     (if (not (rest l)) 
;;;;       (eval (pop (pop l)) xi (pop (pop (rest l))))
;;;;       (eval (pop (pop l)) (iter (rest l)) (pop (pop (rest l))))))
;;;;   (iter 'organism x))
;;;; 
;;;; (eval-organism organism 4)


;; genes.clj ends here
