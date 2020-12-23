package Purse;

import java.util.Arrays;
import java.util.Scanner;

public class Purse {

    enum LCS {PRE_PERSO, USE, BLOCKED, DEAD}

    private int[] userPin;
    private int[] adminPin;

    private final int maxUserTries;
    private final int maxAdminTries;
    private final int maxTrans;
    private final int maxBalance;
    private final int maxCreditAmount;
    private final int maxDebitAmount;

    private int userTriesLeft;
    private int adminTriesLeft;
    private int balance;
    private int transLeft;
    private boolean userAuthenticate;
    private boolean adminAuthenticate;
    private LCS lifeCycleState;

    public Purse(int maxUserTries, int maxAdminTries, int maxTrans, int maxBalance, int maxCreditAmount, int maxDebitAmount, int[] userPin, int[] adminPin) {
        lifeCycleState = LCS.PRE_PERSO;
        this.userPin = userPin;
        this.adminPin = adminPin;

        this.maxUserTries = maxUserTries;
        this.maxAdminTries = maxAdminTries;
        this.maxTrans = maxTrans;
        this.maxBalance = maxBalance;
        this.maxCreditAmount = maxCreditAmount;
        this.maxDebitAmount = maxDebitAmount;

        userTriesLeft = maxUserTries;
        adminTriesLeft = maxAdminTries;
        balance = 0;
        transLeft = maxTrans;
        userAuthenticate = false;
        adminAuthenticate = false;
        lifeCycleState = LCS.USE;
    }

    public Purse(int[] userPin, int[] adminPin) {
        this(3, 4, 500, 100, 50, 30, userPin, adminPin);
    }

    public boolean verifyPINUser(int[] PINCode) {
        if (PINCode.length != userPin.length) {
            return false;
        }
        if (!Arrays.equals(PINCode, userPin)) {
            return false;
        }
        return true;
    }

    public boolean verifyPINAdmin(int[] PINCode) {
        if (PINCode.length != adminPin.length) {
           return false;
        }
        if (!Arrays.equals(PINCode, adminPin)) {
            return false;
        }
        return true;
    }

    private int[] saisiPin() {
        String str = "";
        try {
            Scanner sc = new Scanner(System.in);
            str = sc.next();
        } catch (Exception ignored) {}

        String[] strTab = str.split("");
        int[] pin = new int[strTab.length];
        for (int i = 0; i < strTab.length; i++) {
            pin[i] = Integer.parseInt(strTab[i]);
        }
        return pin;
    }

    private boolean getIdentificationUser () {
        System.out.println("Taille du code pin " + userPin.length);
        int[] pinTab = saisiPin();

        if (verifyPINUser(pinTab)) {
            System.out.println("Code correct");
            lifeCycleState = LCS.USE;
            userTriesLeft = maxUserTries;
            userAuthenticate = true;
        } else {
            userTriesLeft--;
            System.out.println("Code incorrect, essai restant : " + userTriesLeft);
        }

        if (userTriesLeft == 0) {
            System.out.println("Vous avez fait trop d'erreur, vous êtes bloqué.");
            lifeCycleState = LCS.BLOCKED;
        }

        return userAuthenticate;
    }

    private boolean getIdentificationAdmin () {
        System.out.println("Taille du code pin : " + adminPin.length);
        int[] pinTab = saisiPin();

        if (verifyPINAdmin(adminPin)) {
            System.out.println("Code correct");
            lifeCycleState = LCS.USE;
            adminTriesLeft = maxAdminTries;
            adminAuthenticate = true;
        } else {
            adminTriesLeft--;
            System.out.println("Code incorrect, essai restant : " + adminTriesLeft);
        }

        if (adminTriesLeft == 0) {
            lifeCycleState = LCS.DEAD;
        }
        return adminAuthenticate;
    }

    public void PINChangeUnblock() {
        if (lifeCycleState == LCS.BLOCKED) {
            if (!adminAuthenticate) {
                adminAuthenticate = getIdentificationAdmin();
            }
            if (adminAuthenticate) {
                adminTriesLeft = maxAdminTries;
                userTriesLeft = maxUserTries;
                adminAuthenticate = false;
            }
        }
    }

    public void beginTransactionDebit(int amount) {
        if (lifeCycleState != LCS.USE) {
            System.out.println("Cette carte n'est pas en état d'être utilisé pour un débit.");
            userAuthenticate = false;
            return;
        }

        if (!userAuthenticate) {
            userAuthenticate = getIdentificationUser();
        }

        if (!userAuthenticate) {
            System.out.println("Erreur dans l'identification, veuillez réessayer.");
            userAuthenticate = false;
            return;
        }

        if (balance - amount < 0) {
            System.out.println("Erreur vous ne possedez pas assez d'argent pour retirer la somme de : " + amount + ".");
            userAuthenticate = false;
            return;
        }

        if (amount > maxDebitAmount) {
            System.out.println("Vous ne pouvez pas dépasser le plafond.");
            userAuthenticate = false;
            return;
        }

        if (transLeft == 0) {
            System.out.println("Erreur vous ne pouvez plus faire de transactions, vous avez dépasser le nombre autorisé");
            userAuthenticate = false;
            return;
        }

        if (transLeft -1 == 0) {
            System.out.println("Ceci est votre dernière transactions autorisée");
            lifeCycleState = LCS.DEAD;
        }

        System.out.println("Retrait de : " + amount);
        transLeft--;
        balance -= amount;
    }

    public void beginTransactionCredit(int amount) {
        if (lifeCycleState != LCS.USE) {
            System.out.println("Cette carte n'est pas en état d'être utilisé pour un crédit.");
            userAuthenticate = false;
            return;
        }

        if (!userAuthenticate) {
            userAuthenticate = getIdentificationUser();
        }

        if (!userAuthenticate) {
            System.out.println("Erreur dans l'identification, veuillez réessayer.");
            userAuthenticate = false;
            return;
        }

        if (amount < 0) {
            System.out.println("Erreur vous ne pouvez pas créditer une somme de 0.");
            userAuthenticate = false;
            return;
        }

        if (balance + amount > maxBalance) {
            System.out.println("Erreur vous ne pouvez pas ajouter cette somme sur votre compte, vous dépasser le plafond.");
            userAuthenticate = false;
            return;
        }

        if (amount > maxCreditAmount) {
            System.out.println("Vous ne pouvez pas dépasser le plafond.");
            userAuthenticate = false;
            return;
        }

        if (transLeft == 0) {
            System.out.println("Erreur vous ne pouvez plus faire de transactions, vous avez dépasser le nombre autorisé");
            userAuthenticate = false;
            return;
        }

        if (transLeft -1 == 0) {
            System.out.println("Ceci est votre dernière transactions autorisée");
            lifeCycleState = LCS.DEAD;
        }

        System.out.println("Ajout de : " + amount);
        transLeft--;
        balance += amount;
    }

    public void commitTransactionDebit() {

    }

    public void commitTransactionCredit() {
         userAuthenticate = false;
    }

    public int getData() {
        if (!userAuthenticate) {
            userAuthenticate = getIdentificationUser();
        }
        if (userAuthenticate && lifeCycleState == LCS.USE) {
            userAuthenticate = false;
            return balance;
        }
        return -1;
    }
}

