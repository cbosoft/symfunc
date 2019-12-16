;; GENES.clj

;; GENE: a functional block, represented using clojure's record.
(defrecord Gene [
                 doc          ;; [String] A user readable string giving a concise description 
                              ;; of the gene

                 function     ;; [IFn] The function the gene executes: normally an anonymous 
                              ;; function, but any function that accepts two or more inputs is 
                              ;; valid.

                 default-arg  ;; [Number] the value of the default argument to the function.

                 can-tree     ;; [String]

                 ;; when FUNCTION is executed, ARG is first param to func, followed by the value(s).
                 ])

;; GENOME: a list of genes.
(def genome [
             (->Gene "add" + 1 true)
             (->Gene "value minus arg" (fn [arg value] (- value arg)) 1 false)
             (->Gene "arg minus value" (fn [arg & values] (apply - arg values)) 1 true)
             (->Gene "mutiply" * 2 true)
             (->Gene "value divide by arg" (fn [arg value] (/ value arg)) 1 false)
             (->Gene "arg divide by value" (fn [arg & values] (apply / arg values)) 1 true)
             ])

(def ^:dynamic input-value)



;; ORGANISM: a collection of genes working together -> a FUNCTION

;; TODO: change so organism is nested list of gene records
(defn make-organism [indices]
  (let [gene (get genome (first indices))
        gene-func [(:function gene) (:default-arg gene)] 
        rest-indices (rest indices)]
    (if (empty? rest-indices) (seq (conj gene-func 'input-value))
      (seq (conj gene-func (make-organism rest-indices))))))

(defn test-organism [organism input]
  (binding [input-value input]
    (eval organism)))

;; MUTATION: perturbation of an organisms genes
(defn mutate-organism [organism]
  ;; TODO
  )

;; genes.clj ends here
