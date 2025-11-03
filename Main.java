class No {
    int chave;
    String valor;
    int proximo;
    
    public No(int chave, String valor) {
        this.chave = chave;
        this.valor = valor;
        this.proximo = -1;
    }
}

class TabelaDispersao {
    private No[] tabela;
    private int m;
    private int proxLivre;
    
    public TabelaDispersao(int tamanho) {
        this.m = tamanho;
        this.tabela = new No[m];
        this.proxLivre = 0;
    }
    
    private int hash(int chave) {
        return chave % m;
    }
    
    public void inserir(int chave, String valor) {
        int endereco = hash(chave);
        
        // Verifica se a chave ja existe
        if (buscar(chave) != null) {
            System.out.println("Chave " + chave + " ja cadastrada");
            return;
        }
        
        // Posicao vazia - insere diretamente
        if (tabela[endereco] == null) {
            tabela[endereco] = new No(chave, valor);
            System.out.println("Chave " + chave + " inserida no endereco " + endereco);
        } else {
            // Colisao - procura posicao livre
            int posLivre = encontrarPosicaoLivre();
            if (posLivre == -1) {
                System.out.println("Tabela cheia!");
                return;
            }
            
            // Insere na posicao livre
            tabela[posLivre] = new No(chave, valor);
            
            // Encadeia na lista
            int atual = endereco;
            while (tabela[atual].proximo != -1) {
                atual = tabela[atual].proximo;
            }
            tabela[atual].proximo = posLivre;
            
            System.out.println("Chave " + chave + " inserida no endereco " + posLivre + " (colisao)");
        }
    }
    
    public String buscar(int chave) {
        int endereco = hash(chave);
        
        if (tabela[endereco] == null) {
            return null;
        }
        
        int atual = endereco;
        while (atual != -1) {
            if (tabela[atual].chave == chave) {
                return tabela[atual].valor;
            }
            atual = tabela[atual].proximo;
        }
        
        return null;
    }
    
    public void remover(int chave) {
        int endereco = hash(chave);
        
        if (tabela[endereco] == null) {
            System.out.println("Chave " + chave + " nao encontrada");
            return;
        }
        
        // Caso especial: remover o primeiro da lista
        if (tabela[endereco].chave == chave) {
            if (tabela[endereco].proximo == -1) {
                tabela[endereco] = null;
            } else {
                int proxPos = tabela[endereco].proximo;
                tabela[endereco] = tabela[proxPos];
                tabela[proxPos] = null;
            }
            System.out.println("Chave " + chave + " removida");
            return;
        }
        
        // Percorre a lista para encontrar o elemento
        int anterior = endereco;
        int atual = tabela[endereco].proximo;
        
        while (atual != -1) {
            if (tabela[atual].chave == chave) {
                tabela[anterior].proximo = tabela[atual].proximo;
                tabela[atual] = null;
                System.out.println("Chave " + chave + " removida");
                return;
            }
            anterior = atual;
            atual = tabela[atual].proximo;
        }
        
        System.out.println("Chave " + chave + " nao encontrada");
    }
    
    private int encontrarPosicaoLivre() {
        for (int i = 0; i < m; i++) {
            if (tabela[i] == null) {
                return i;
            }
        }
        return -1;
    }
    
    public void exibir() {
        System.out.println("\n=== Tabela de Dispersao ===");
        for (int i = 0; i < m; i++) {
            System.out.print("Posicao " + i + ": ");
            if (tabela[i] == null) {
                System.out.println("vazio");
            } else {
                int atual = i;
                while (atual != -1) {
                    System.out.print("[" + tabela[atual].chave + ":" + tabela[atual].valor + "]");
                    atual = tabela[atual].proximo;
                    if (atual != -1) {
                        System.out.print(" -> ");
                    }
                }
                System.out.println();
            }
        }
        System.out.println();
    }
}

public class Main {
    public static void main(String[] args) {
        TabelaDispersao tabela = new TabelaDispersao(7);
        
        System.out.println("=== Teste de Insercao ===");
        tabela.inserir(48, "Valor48");
        tabela.inserir(3, "Valor03");
        tabela.inserir(80, "Valor80");
        tabela.inserir(31, "Valor31");
        tabela.inserir(20, "Valor20");
        
        tabela.exibir();
        
        System.out.println("=== Teste de Busca ===");
        System.out.println("Buscar 48: " + tabela.buscar(48));
        System.out.println("Buscar 31: " + tabela.buscar(31));
        System.out.println("Buscar 99: " + tabela.buscar(99));
        
        System.out.println("\n=== Teste de Remocao ===");
        tabela.remover(31);
        tabela.exibir();
        
        tabela.remover(48);
        tabela.exibir();
        
        System.out.println("=== Teste de Colisao ===");
        TabelaDispersao tabela2 = new TabelaDispersao(7);
        tabela2.inserir(28, "A");
        tabela2.inserir(35, "B");
        tabela2.inserir(14, "C");
        tabela2.inserir(70, "D");
        tabela2.inserir(19, "E");
        tabela2.exibir();
    }
}