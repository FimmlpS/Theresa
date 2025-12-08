package Theresa.helper;

import Theresa.card.AbstractTheresaCard;
import Theresa.patch.OtherEnum;
import com.megacrit.cardcrawl.cards.AbstractCard;

import java.util.ArrayList;

public class CivilightHelper {
    public static boolean ownQuiZa = false;

    public static ArrayList<AbstractCard> cardSaveList = new ArrayList<>();
    public static ArrayList<AbstractCard> civilightThisCombat = new ArrayList<>();

    public static void atBattleStart(){
        civilightThisCombat.clear();
    }

    public static boolean contains(AbstractCard card){
        for(AbstractCard c : civilightThisCombat){
            if(c.cardID.equals(card.cardID) && c.uuid == card.uuid){
                return true;
            }
        }
        return false;
    }

    public static AbstractCard getInstanceFromSave(AbstractCard card){
        for(AbstractCard c : cardSaveList){
            if(c.cardID.equals(card.cardID) && c.uuid == card.uuid){
                return c;
            }
        }
        return null;
    }
}
