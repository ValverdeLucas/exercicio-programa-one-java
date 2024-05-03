import java.util.Collections;
import java.util.Scanner;

public class Main {

    public static double validateAmount(Scanner input) {
        while (!input.hasNextDouble()) {
            System.out.println("Por favor, insira um valor válido para a operação!");
            input.next();
        }
        return input.nextDouble();
    }

    public static int validateOption(Scanner input) {
        while (!input.hasNextInt()) {
            System.out.println("Por favor, insira um valor númerico válido:");
            input.next();
        }
        return input.nextInt();
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        var creditCard = new CreditCard();
        int menuOption = 0;

        String menu = """      
                        
                Menu
                                
                1- Inserir saldo em conta
                2- Incluir item no carrinho
                3- Remover item do carrinho
                4- Conferir carrinho e finalizar compra
                5- Sair
                                
                Digite a opção desejada:             
                """;

        while (menuOption != 5) {

            System.out.println(menu);
            menuOption = validateOption(input);

            switch (menuOption) {

                case 1:
                    System.out.println(String.format("Insira o valor que deseja depositar em sua conta:"));
                    double cardLimit = validateAmount(input);

                    if (cardLimit < 0) {
                        System.out.println("O valor digitado para o depósito é inválido! Refaça a operação com um valor positivo.");
                    }
                    if (cardLimit >= 0) {
                        creditCard.setAccountLimit(cardLimit);
                        creditCard.setAccountAmount(cardLimit);
                        System.out.println(String.format("""
                                ***************************************
                                Foram incluídos R$%.2f em sua conta.
                                Saldo atual: R$%.2f
                                ***************************************
                                """, cardLimit, creditCard.getAccountAmount()));
                    }
                    break;

                case 2:
                    int finishShopping = 1;
                    while (finishShopping != 0) {

                        System.out.println(String.format("Digite a descrição do produto:"));
                        String name = input.next();

                        System.out.println(String.format("Digite o valor do produto:"));
                        double price = input.nextDouble();

                        var product = new Product(name, price);
                        boolean includedOnCart = creditCard.insertOnCart(product);

                        if (includedOnCart) {
                            System.out.println("""
                                    Item adicionado ao carrinho!
                                                                    
                                    Deseja adicionar um novo item ao carrinho? (Digite 1 para sim ou 0 para não):""");
                            finishShopping = validateOption(input);
                        } else {
                            System.out.println("Saldo insuficiente para realizar a compra!");
                            finishShopping = 0;
                            menuOption = 5;
                        }
                    }
                    break;

                case 3:

                    int removeItem = 0;
                    int finishMenu = 1;

                    while (finishMenu != 0) {
                        for (int i = 0; i < creditCard.getCart().size(); i++) {
                            System.out.println(i + ":" + " | " + creditCard.getCart().get(i));
                        }
                        System.out.println("Escolha qual dos itens acima você quer remover do seu carrinho:");
                        removeItem = input.nextInt();

                        boolean isOnCart = creditCard.removeFromCart(creditCard.getCart().get(removeItem));

                        if (isOnCart) {
                            System.out.println(String.format("""
                                    Item removido do carrinho!
                                                                        
                                    Saldo atual: R$%.2f
                                                                    
                                    Deseja remover um outro item do carrinho? (Digite 1 para sim ou 0 para não):""", creditCard.getAccountAmount()));
                            finishMenu = validateOption(input);
                        } else {
                            System.out.println("O produto não existe no carrinho!");
                            finishMenu = 0;
                        }
                    }
                    break;

                case 4:

                    double finalPrice = 0;
                    int confirmPurchase = 1;
                    while (confirmPurchase != 0) {

                        System.out.println("****** Itens no carrinho ******\n");
                        Collections.sort(creditCard.getCart());
                        for (Product product : creditCard.getCart()) {
                            System.out.println(product.getName() + " | " + "R$" + product.getPrice());
                        }
                        System.out.println("\n************\n");

                        System.out.println("Deseja finalizar a sua compra? (Digite 1 para sim ou 0 para não)");
                        confirmPurchase = validateOption(input);

                        if (confirmPurchase == 1) {
                            System.out.println("Obrigado por utilizar os nossos serviços!\nItens comprados:\n");
                            for (Product product : creditCard.getCart()) {
                                System.out.println(product.getName() + " | " + "R$" + product.getPrice());
                                finalPrice += product.getPrice();
                            }
                            System.out.println("Total da compra: R$" + finalPrice);
                            System.out.println("\n************ Sessão encerrada ************");
                            confirmPurchase = 0;
                            menuOption = 5;
                        } else {
                            confirmPurchase = 0;
                        }
                    }
                    break;


                case 5:
                    System.out.println("""
                             ************ Sessão encerrada ************
                            """);
                    break;

            }
        }
    }
}