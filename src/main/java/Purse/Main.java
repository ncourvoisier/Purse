package Purse;

import java.util.Scanner;

public class Main {

    public static void main (String [] args) {
        Scanner sc = new Scanner(System.in);
        Purse purse = new Purse(new int[] {1,2,3,4}, new int[] {1,2,3,4,5,6});
        int choix;
        System.out.println("\nBienvenue à la banque.");
        do {

            System.out.println("\nQue souhaitez-vous faire ?");
            System.out.println("1. Créditer une somme sur votre compte");
            System.out.println("2. Débiter une somme sur votre compte");
            System.out.println("3. Consulter votre solde");
            System.out.println("4. Débloquer la carte");
            System.out.println("0. Quitter");
            choix = sc.nextInt();

            switch (choix) {
                case 1:
                    System.out.print("\nSaisir la somme à créditer : ");
                    int amountC = sc.nextInt();
                    purse.beginTransactionCredit(amountC);
                    purse.commitTransactionCredit();
                    break;

                case 2:
                    System.out.print("\nSaisir la somme à débiter : ");
                    int amountD = sc.nextInt();
                    purse.beginTransactionDebit(amountD);
                    purse.commitTransactionDebit();
                    break;

                case 3:
                    System.out.print("\nAffichage du solde : "+purse.getData());
                    break;

                case 4:
                    System.out.println("Déblocage de la carte");
                    purse.PINChangeUnblock();
                    break;

                default:
            }
        } while (choix != 0);

/*
        Purse purse = new Purse(new int[] {1,2,3,4}, new int[] {1,2,3,4,5,6});
        System.out.println("Opération de crédit : 30 euros");
        purse.beginTransactionCredit(30);
        purse.commitTransactionCredit();

        System.out.println("Affichage du solde : "+purse.getData());

        System.out.println("Opération de débit : 20 euros");
        purse.beginTransactionDebit(20);
        purse.commitTransactionDebit();

        System.out.println("Affichage du solde : "+purse.getData());

        System.out.println("Déblocage de la carte");
        purse.PINChangeUnblock();

        System.out.println("Opération de crédit : 15 euros");
        purse.beginTransactionCredit(15);
        purse.commitTransactionCredit();

        System.out.println("Opération de débit : 10 euros");
        purse.beginTransactionDebit(10);
        purse.commitTransactionDebit();*/
    }
}
