package org.example.application;

import org.example.model.entities.Garagem;

import java.security.InvalidParameterException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    static Scanner keyboard = new Scanner(System.in);

    private static final double PESO_MAXIMO = 30;
    private static final int VAGOES_INICIAIS = 50;
    private static final int LOCOMOTIVA_INICIAIS = 10;
    private static final int CAPACIDADE_CARGA = 6;

    private static String path = "Ferroviaria\\src\\main\\java\\org\\example\\model\\CSV\\implementation\\data\\"; // <---- ENDEREÇO DA PASTA CONTENDO OS ARQUIVOS CSV
    private static Garagem garagem = new Garagem(path);

    public static void main(String[] ARGS) {
        while (menu());
    }

    public static boolean menu() { // Menu interativo
        System.out.println("-------------------------------------------------------");
        System.out.println("          Centro de controle da ferroviária            ");
        System.out.println("-------------------------------------------------------");
        System.out.println("|                 Menu de opções                      |");
        System.out.println("|           Digite a opção desejada                   |");
        System.out.println("|            1.  Criar um trem                        |");
        System.out.println("|            2.  Editar um trem                       |");
        System.out.println("|            3.  Mostrar as garagens                  |");
        System.out.println("|            4.  Pesquisar vagões e locomotivas       |");
        System.out.println("|            5.  Desfazer um trem                     |");
        System.out.println("|            9.  Encerrar o programa                  |");
        System.out.println("|-----------------------------------------------------|");
        String opcao = keyboard.next();
        switch (opcao) {
            case "1": // Criação de um trem
                criaTrem();
                menuEditar();
                break;
            case "2":
                menuEditar();
                break;
            case "3":
                System.out.println(garagem);
                break;
            case "4":
                mostrarGaragem();
                break;
            case "5":
                desfazerTrem();
                break;
            case "9":
                System.out.println("------------------------------------------------------");
                System.out.println("               **Fim do programa**                    ");
                System.out.println("------------------------------------------------------");
                return false;
            default:
                opcaoInvalida();
                break;
        }
        return true;
    }

    public static void opcaoInvalida() {
        System.out.println("------------------------------------------------------");
        System.out.println("                Opção inválida                        ");
        System.out.println("------------------------------------------------------");
    }

    public static void escolheMenu() {
        System.out.println("------------------------------------------------------");
        System.out.println("       Escolha o que deseja fazer                     ");
        System.out.println("      1. Voltar ao menu anterior                      ");
        System.out.println("      2. Voltar ao menu principal                     ");
        System.out.println("------------------------------------------------------");
    }

    public static void criaTrem() {
        try {
            System.out.println("Insira um identificador para o trem:");
            int idTrem = keyboard.nextInt();

            garagem.cadastrarTrem(idTrem);
            System.out.println(garagem);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Input inválido");
            keyboard.nextLine();
        }
    }

    public static void adicionaLocomotiva() {
        try {
            System.out.println("Insira o ID do trem: ");
            int idTrem = keyboard.nextInt();

            System.out.println("Insira o ID da locomotiva: ");
            int idLocomotiva = keyboard.nextInt();

            garagem.alocarLocomotiva(garagem.getLocomotiva(idLocomotiva), garagem.getTrem(idTrem));
            System.out.println(garagem);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Input invalido");
            keyboard.nextLine();
        }
    }

    public static void adicionaVagao() {
        try {
            System.out.println("Insira o ID do trem:");
            int idTrem = keyboard.nextInt();

            System.out.println("Insira o ID do vagão:");
            int idVagao = keyboard.nextInt();

            garagem.alocarVagao(garagem.getVagao(idVagao), garagem.getTrem(idTrem));
            System.out.println(garagem);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Input inválido");
            keyboard.nextLine();
        }
    }

    public static void removeLocomotiva() {
        try {
            System.out.println("Insira o ID do trem:");
            int idTrem = keyboard.nextInt();

            garagem.desacoplarLocomotiva(garagem.getTrem(idTrem));
            System.out.println(garagem);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Input inválido");
            keyboard.nextLine();
        }
    }

    public static void removeVagao() {
        try {
            System.out.println("Insira o ID do trem:");
            int idTrem = keyboard.nextInt();

            garagem.desacoplarVagao(garagem.getTrem(idTrem));
            System.out.println(garagem);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Input inválido");
            keyboard.nextLine();
        }
    }

    public static void desfazerTrem() {
        try {
            System.out.println("Insira o ID do trem:");
            int idTrem = keyboard.nextInt();

            garagem.desfazerTrem(garagem.getTrem(idTrem));
            System.out.println(garagem);
        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Input inválido");
            keyboard.nextLine();
        }
    }

    public static void mostrarGaragem() {
        try {
            System.out.println("Insira o ID da unidade: (Ex: 'L1', 'V2')");
            String input = keyboard.next().toUpperCase();
            if (input.startsWith("L")) {
                String idChar = input.replace("L", "");
                int id = Integer.parseInt(idChar);

                try {
                    System.out.println("Unidade alocada ao: T" + garagem.inspecionarLocomotiva(id));
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                }
            } else if (input.startsWith("V")) {
                String idChar = input.replace("L", "");
                int id = Integer.parseInt(idChar);

                try {
                    System.out.println("Unidade alocada ao: T" + garagem.inspecionarVagao(id));
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                }
            } else {
                System.out.println("O ID inserido não e válido");
            }

        } catch (InvalidParameterException e) {
            System.out.println(e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Input inválido");
            keyboard.nextLine();
        }
    }

    public static boolean opcaoDeMenu() {
        boolean trava = true;
        do {
            escolheMenu();
            String menuOpcoes = keyboard.next();
            switch (menuOpcoes) {
                case "1":
                    trava = false;
                    return true;
                case "2":
                    trava = false;
                    return false;
                default:
                    opcaoInvalida();
                    return true;
            }
        } while (trava == true);
    }

    public static void menuEditar() {
        boolean switchEdicao = true;
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("    Digite a opção do que você deseja editar no trem:  ");
            System.out.println("-------------------------------------------------------");
            System.out.println("|   1.  Adicionar uma locomotiva ao trem              |");
            System.out.println("|   2.  Adicionar um vagão ao trem                    |");
            System.out.println("|   3.  Remover a última locomotiva do trem           |");
            System.out.println("|   4.  Remover o último vagão do trem                |");
            System.out.println("|   5.  Mostrar garagens                              |");
            System.out.println("|   9.  Encerrar edição do trem                       |");
            System.out.println("|-----------------------------------------------------|");
            String opcaoEdicao = keyboard.next();
            switch (opcaoEdicao) {
                case "1":
                    adicionaLocomotiva();
                    switchEdicao = opcaoDeMenu();
                    break;
                case "2":
                    adicionaVagao();
                    switchEdicao = opcaoDeMenu();
                    break;
                case "3": // remove locomotiva
                    removeLocomotiva();
                    switchEdicao = opcaoDeMenu();
                    break;
                case "4": // remove vagao
                    removeVagao();
                    switchEdicao = opcaoDeMenu();
                    break;
                case "5": // Mostra locomotivas livres
                    System.out.println(garagem);
                    switchEdicao = opcaoDeMenu();
                    break;
                case "6": // mostra vagoes livres
                    switchEdicao = opcaoDeMenu();
                    break;

                case "9":
                    switchEdicao = false;
                    break;
                default:
                    opcaoInvalida();
                    break;
            }
        } while (switchEdicao == true);
    }
}
