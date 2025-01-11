public class Main {

        public static void main(String[] args) {
            Arvore arvore = new Arvore();

            // Inserindo elementos na árvore AVL
            arvore.inserir(10);
            arvore.inserir(20);
            arvore.inserir(30);
            arvore.inserir(-2);
            arvore.inserir(40);
            arvore.inserir(50);
            arvore.inserir(25);
            arvore.inserir(12);
            arvore.inserir(27);
            arvore.inserir(6);


            System.out.println("Árvore após Inserção");
            arvore.imprimirArvore();


//            System.out.println("Arvore em Ordem:");
//            arvore.emOrdem(arvore.raiz);
//            System.out.println(" ");
//            System.out.println("Arvore em Pré Ordem:");
//            arvore.preOrdem(arvore.raiz);
//            System.out.println(" ");
//            System.out.println("Arvore em Pós Ordem:");
//            arvore.posOrdem(arvore.raiz);
//            System.out.println(" ");


            arvore.remover(40);
            System.out.println("Árvore após a remoção do valor 40:");
            arvore.imprimirArvore();

            arvore.remover(12);
            System.out.println("Árvore após a remoção do valor 12:");
            arvore.imprimirArvore();

            arvore.remover(20);
            System.out.println("Árvore após a remoção do valor 20:");
            arvore.imprimirArvore();


        }



}
