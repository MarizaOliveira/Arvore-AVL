
import org.w3c.dom.ls.LSOutput;

import java.util.*;

class Elemento {
    public int valor;
    public Elemento esquerda;
    public Elemento direita;
    public int altura;

    public Elemento(int novoValor) {
        this.valor = novoValor;
        this.esquerda = null;
        this.direita = null;
        this.altura = 1;
    }
}

public class Arvore {
    public Elemento raiz;

    public void inserir(int valor) {
        Elemento novoElemento = new Elemento(valor);
        if (raiz == null) {
            raiz = novoElemento;
            return;
        }

        Stack<Elemento> pilha = new Stack<>();
        Elemento atual = raiz;

        while (true) {
            pilha.push(atual);
            if (valor < atual.valor) {
                if (atual.esquerda == null) {
                    atual.esquerda = novoElemento;
                    break;
                }
                atual = atual.esquerda;
            } else if (valor > atual.valor) {
                if (atual.direita == null) {
                    atual.direita = novoElemento;
                    break;
                }
                atual = atual.direita;
            } else {
                // Valor duplicado, ignorar
                return;
            }
        }

        while (!pilha.isEmpty()) {
            atual = pilha.pop();

            atual.altura = 1 + Math.max(altura(atual.esquerda), altura(atual.direita));

            int balanceamento = obterBalanceamento(atual);

            // rotação direita
            if (balanceamento > 1 && valor < atual.esquerda.valor) {
                if (!pilha.isEmpty()) {
                    Elemento pai = pilha.peek();
                    if (pai.esquerda == atual) {
                        pai.esquerda = rotacaoDireita(atual);
                    } else {
                        pai.direita = rotacaoDireita(atual);
                    }
                } else {
                    raiz = rotacaoDireita(atual);
                }

                // rotação esqueda
            } else if (balanceamento < -1 && valor > atual.direita.valor) {
                if (!pilha.isEmpty()) {
                    Elemento pai = pilha.peek();

                    if (pai.esquerda == atual) {
                        pai.esquerda = rotacaoEsquerda(atual);
                    } else {
                        pai.direita = rotacaoEsquerda(atual);
                    }
                } else {
                    raiz = rotacaoEsquerda(atual);
                }

                // Rotação direita-esquerda
            } else if (balanceamento > 1 && valor > atual.esquerda.valor) {

                atual.esquerda = rotacaoEsquerda(atual.esquerda);

                if (!pilha.isEmpty()) {
                    Elemento pai = pilha.peek();

                    if (pai.esquerda == atual) {
                        pai.esquerda = rotacaoDireita(atual);
                    } else {
                        pai.direita = rotacaoDireita(atual);
                    }

                } else {
                    raiz = rotacaoDireita(atual);
                }
                //balanceamento esquerda-direita
            } else if (balanceamento < -1 && valor < atual.direita.valor) {
                atual.direita = rotacaoDireita(atual.direita);
                if (!pilha.isEmpty()) {
                    Elemento pai = pilha.peek();
                    if (pai.esquerda == atual) {
                        pai.esquerda = rotacaoEsquerda(atual);
                    } else {
                        pai.direita = rotacaoEsquerda(atual);
                    }
                } else {
                    raiz = rotacaoEsquerda(atual);
                }
            }
        }
    }

    public void emOrdem(Elemento atual) {
        if (atual != null) {
            emOrdem(atual.esquerda);
            System.out.print(atual.valor+",");
            emOrdem(atual.direita);
        }
    }

    public void preOrdem(Elemento atual) {
        if (atual != null) {
            System.out.print(atual.valor+",");
            preOrdem(atual.esquerda);
            preOrdem(atual.direita);
        }
    }

    public void posOrdem(Elemento atual) {
        if (atual != null) {
            posOrdem(atual.esquerda);
            posOrdem(atual.direita);
            System.out.print(atual.valor+",");
        }
    }

    //metodo remover

    public void remover(int valor) {
        if (raiz == null) {
            return;
        }

        Stack<Elemento> pilha = new Stack<>();
        Elemento atual = raiz;
        Elemento pai = null;

        while (atual != null && atual.valor != valor) {

            pilha.push(atual);
            pai = atual;

            if (valor < atual.valor) {
                atual = atual.esquerda;
            } else {
                atual = atual.direita;
            }
        }

        if (atual == null) {
            System.out.println("Valor não encontrado na árvore !!!");
            return;
        }

        if (atual.esquerda==null || atual.direita == null) {
            Elemento novoAtual;

            if (atual.esquerda ==null) {
                novoAtual = atual.direita;
            } else {
                novoAtual = atual.esquerda;
            }

            if (pai == null) {
                raiz = novoAtual;
            } else if (pai.esquerda == atual) {
                pai.esquerda = novoAtual;
            } else {
                pai.direita = novoAtual;
            }
        } else {
            Elemento sucessor=encontrarSucessor(atual);
            int valorSucessor=sucessor.valor;
            remover(valorSucessor);
            atual.valor = valorSucessor;
        }

        while (!pilha.isEmpty()) {
            atual = pilha.pop();
            atual.altura = 1 + Math.max(altura(atual.esquerda), altura(atual.direita));

            int balanceamento = obterBalanceamento(atual);
            // rotação direita
            if (balanceamento > 1 && obterBalanceamento(atual.esquerda) >= 0) {
                if (!pilha.isEmpty()) {
                    Elemento paiAtual = pilha.peek();
                    if (paiAtual.esquerda == atual) {
                        paiAtual.esquerda = rotacaoDireita(atual);
                    } else {
                        paiAtual.direita = rotacaoDireita(atual);
                    }
                } else {
                    raiz = rotacaoDireita(atual);
                }

                // rotação esquerda
            } else if (balanceamento < -1 && obterBalanceamento(atual.direita) <= 0) {
                if (!pilha.isEmpty()) {
                    Elemento paiAtual = pilha.peek();
                    if (paiAtual.esquerda == atual) {
                        paiAtual.esquerda = rotacaoEsquerda(atual);
                    } else {
                        paiAtual.direita = rotacaoEsquerda(atual);
                    }
                } else {
                    raiz = rotacaoEsquerda(atual);
                }

              // esqueda-direita
            } else if (balanceamento > 1 && obterBalanceamento(atual.esquerda) < 0) {
                atual.esquerda=rotacaoEsquerda(atual.esquerda);

                if (!pilha.isEmpty()) {

                    Elemento paiAtual = pilha.peek();
                    if (paiAtual.esquerda==atual) {
                        paiAtual.esquerda=rotacaoDireita(atual);
                    } else {
                        paiAtual.direita = rotacaoDireita(atual);
                    }
                } else {
                    raiz = rotacaoDireita(atual);
                }


                //esquerda-direita;
            } else if (balanceamento < -1 && obterBalanceamento(atual.direita) > 0) {
                atual.direita = rotacaoDireita(atual.direita);

                if (!pilha.isEmpty()) {
                    Elemento paiAtual = pilha.peek();
                    if (paiAtual.esquerda == atual) {

                        paiAtual.esquerda = rotacaoEsquerda(atual);
                    } else {
                        paiAtual.direita = rotacaoEsquerda(atual);
                    }
                } else {
                    raiz = rotacaoEsquerda(atual);
                }




            }

        }
    }

    private Elemento encontrarSucessor(Elemento nodo) {
        Elemento atual = nodo.direita;

        while (atual.esquerda != null) {
            atual = atual.esquerda;

        }
        return atual;
    }

    private int altura(Elemento nodo) {
        if (nodo == null) {
            return 0;
        }

        return nodo.altura;
    }

    private int obterBalanceamento(Elemento nodo) {

        if (nodo == null) {
            return 0;
        }

        return altura(nodo.esquerda) - altura(nodo.direita);
    }

    private Elemento rotacaoDireita(Elemento y) {
        Elemento x = y.esquerda;
        Elemento T2 = x.direita;


        x.direita = y;
        y.esquerda = T2;

        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;
        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;

        return x;
    }

    private Elemento rotacaoEsquerda(Elemento x) {
        Elemento y = x.direita;
        Elemento T2 = y.esquerda;



        y.esquerda = x;
        x.direita = T2;

        x.altura = Math.max(altura(x.esquerda), altura(x.direita)) + 1;
        y.altura = Math.max(altura(y.esquerda), altura(y.direita)) + 1;

        return y;
    }


    // impressão da árvore no formato de um desenho !!!

    private void inserir(List<List<String>> linhas, Elemento nodo, int nivel, int esquerda, int direita) {
        if (nodo == null) {
            return;
        }
        int meio = (esquerda + direita) / 2;
        linhas.get(nivel).set(meio, Integer.toString(nodo.valor));


        inserir(linhas, nodo.esquerda, nivel + 1, esquerda, meio - 1);
        inserir(linhas, nodo.direita, nivel + 1, meio + 1, direita);
    }

    void imprimirArvore() {
        int altura = altura(raiz);
        int maxWidth = (int) Math.pow(2, altura) - 1;
        List<List<String>> linhas = new ArrayList<>();

        for (int i = 0; i < altura; i++) {
            linhas.add(new ArrayList<>(Collections.nCopies(maxWidth, " ")));
        }

        inserir(linhas, raiz, 0, 0, maxWidth - 1);

        for (List<String> linha : linhas) {
            for (String val : linha) {
                System.out.print(val);
            }

            System.out.println();
        }


    }



}