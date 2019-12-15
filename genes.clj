;; GENES.clj

;; GENE: pair of function and number of free parameters it accepts. That is,
;; parameters on top of the main flow of data, parameters which can be mutated
;; as part of the optimisation.

;; GENOME: a list of genes
(defvar genome '((add 1)
                 (multiply 2)))

;; ORGANISM: a collection of genes working together
(defvar organism '((pop genome) (pop (rest genome))))
(print "here")

(defun eval-organism (organism x)
  (defun iter (l xi)
    (print l)
    (if (not (rest l)) 
      (eval (pop (pop l)) xi (pop (pop (rest l))))
      (eval (pop (pop l)) (iter (rest l)) (pop (pop (rest l))))))
  (iter 'organism x))

(eval-organism organism 4)
;; genes.clj ends here
