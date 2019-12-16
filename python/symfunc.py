import numpy as np

class Gene: 

    '''
    Gene: base class of the gene building block. Genes are functional
    operations which can be chained together to create a complex function.
    '''

    mutation_chance = 0.05 # 5% chance of mutating in some manner
    max_arguments = np.inf

    def __init__(self, *args):
        self.args = list(args)


    @staticmethod
    def func(*args):
        raise NotImplementedError

    def eval(self):
        if len(self.args) > self.max_arguments:
            raise Exception(f"Too many arguments to Gene {self}")
        processed_args = list()

        for arg in self.args:
            if isinstance(arg, Gene):
                processed_args.append(arg.eval())
            else:
                processed_args.append(arg)

        return self.func(*processed_args)


class Input(Gene):

    @staticmethod
    def func(*args):
        return args

    def set_args(self, *args):
        self.args = args


class Add1(Gene):

    @staticmethod
    def func(*args):
        assert len(args) == 1
        return np.add(args[0], 1)


class Sum(Gene):

    @staticmethod
    def func(*args):
        return np.sum(args, axis=0)

class Multiply(Gene):

    @staticmethod
    def func(*args):
        rv = 1.0
        for arg in args:
            rv = np.multiply(rv, arg)
        return rv

class Divide(Gene):

    @staticmethod
    def func(*args):
        rv = args[0]
        for arg in args[1:]:
            rv = np.divide(rv, arg)
        return rv


GENOME = [Add1, Multiply, Divide, Sum]


def stitch_genes(genes_tree):
    evaluated_tree = list()
    for element in genes_tree:
        if isinstance(element, list):
            element = stitch_genes(element)
        evaluated_tree.append(element)
    root = evaluated_tree[0]

    if isinstance(root, type):
        # is class, create instance lisp style
        rv = evaluated_tree[0](*evaluated_tree[1:])
    elif isinstance(root, Gene):
        # is instance, ensure is alone
        assert len(evaluated_tree) == 1
        rv = root
    else:
        raise Exception("leaves should be gene or Gene")
    return rv


class Organism:
     
    def __init__(self, genes):
        self.genes = genes

    def __add__(self, other):
        return self.reproduce(other)

    def eval(self):
        stitched_genes = stitch_genes(self.genes)
        return stitched_genes.eval()

    def mutate(self):
        pass # return a new organism which is like self, but altered slightly

    def reproduce(self, other):
        pass # return new organism which inherits from parents (self, other)


three = Input(3)
four = Input(4)
tree = [Multiply, [Sum, three, three], [Add1, four]]

organism = Organism(tree)
print(organism.eval())




#i = Input([2, 3, 4, 5])
#a = Add1(i)
#b = Add1(i)
#c = Sum(a, b)
#print(c.eval())


#a = Sum(Sum(, i), i)
#print(a.eval())
