package Theresa.helper;

import Theresa.card.attack.*;
import Theresa.card.power.*;
import Theresa.card.skill.*;
import Theresa.card.status.Decide;
import Theresa.card.status.Stop;
import Theresa.card.status.TheGift;
import Theresa.relic.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RegisterHelper {
    public static ArrayList<String> startDeck = new ArrayList<>();
    public static ArrayList<String> startRelic = new ArrayList<>();
    public static ArrayList<String> bossRelic = new ArrayList<>();

    public static ArrayList<AbstractCard> getCardsToAdd(){
        ArrayList<AbstractCard> list = new ArrayList<>();

        //BASIC
        list.add(new Strike());
        list.add(new Defend());
        list.add(new Spot());
        list.add(new Wish());
        //list.add(new DustStrike());
        //list.add(new DustDefend());
        list.add(new ADust());

        //COMMON
        list.add(new Oscillation());
        list.add(new Candle());
        //list.add(new DeadOne());
        list.add(new KingKitty());
        list.add(new TowardEndfield());
        list.add(new FromNight());
        list.add(new Iterate());
        list.add(new InFire());
        list.add(new WhereIsHome());
        list.add(new LittleTailor());
        list.add(new Babel());
        list.add(new WeaveSpread());
        list.add(new EmotionSame());
        list.add(new SarkazSee());
        list.add(new AStory());
        list.add(new Hardship());
        list.add(new SkyDisaster());
        list.add(new WrongInteraction());
        list.add(new FlowerSilk());
        list.add(new EndDust());
        list.add(new FallFromMemory());
        list.add(new UnseenFuture());
        list.add(new Silk());
        list.add(new FallMoon());

        //UNCOMMON
        list.add(new FlowerOcean());
        list.add(new ToughHistory());
        list.add(new TimeToDie());
        list.add(new SilkPoem());
        list.add(new Echoism());
        list.add(new PastDust());
        list.add(new YoreLinger());
        list.add(new DeathOfSarkaz());
        list.add(new LightlessShadow());
        list.add(new Forgive());
        list.add(new ThereItIs());
        list.add(new ImperialCourts());
        list.add(new WeaveTomorrow());
        list.add(new HoldDust());
        list.add(new TheAmiya());
        list.add(new TheDoctor());
        list.add(new TheCat());
        list.add(new TheSwordsman());
        list.add(new TheStrategist());
        list.add(new TheKiller());
        list.add(new TheDale());
        list.add(new InsideCrystal());
        list.add(new NarrativeSong());
        list.add(new ConnectedPain());
        list.add(new Terminate());
        list.add(new IdealReflection());
        list.add(new Starter());
        list.add(new PastCallFuture());
        list.add(new IntoHistory());
        list.add(new MindOscillation());
        list.add(new DeadBreath());
        list.add(new Branch());
        list.add(new KingInheritance());
        list.add(new WhenItIsBye());
        list.add(new FromRose());
        list.add(new FutureStop());
        list.add(new Withering());
        list.add(new GoodNight());
        list.add(new BeforeDust());
        list.add(new Wordless());
        list.add(new UnforgivableSin());
        list.add(new NightBeforeWar());
        list.add(new CivilightEterna());
        list.add(new SadDust());
        list.add(new Cure());
        list.add(new FixLife());
        list.add(new SadTime());
        list.add(new StoryTeller());
        list.add(new UnwaveringDust());
        list.add(new Enlightenment());

        //RARE
        list.add(new GuiltyWillEnd());
        list.add(new DustWithLight());
        list.add(new DustFalls());
        list.add(new Finale());
        list.add(new EternalDust());
        list.add(new TheTransformer());
        list.add(new TheBlooder());
        list.add(new TheBanshee());
        list.add(new TheWar());
        list.add(new ThousandsWish());
        list.add(new ManyLight());
        list.add(new OnlyForYou());
        list.add(new TheAnswer());
        list.add(new HeroAndKing());
        list.add(new MemorizedMemory());
        list.add(new DyingRose());
        list.add(new Rare());
        list.add(new MindOcean());
        list.add(new MourningInMourning());
        list.add(new FinalCountdown());

        //OTHER
        list.add(new TheGift());
        list.add(new Originium());
        list.add(new Stop());
        list.add(new Decide());


        return list;
    }

    public static ArrayList<AbstractRelic> getRelicsToAdd(boolean onlyTheresa){
        ArrayList<AbstractRelic> list = new ArrayList<>();
        if(onlyTheresa){
            list.add(new BabelRelic());
            list.add(new UnknownRelic());

            //BOSS
            list.add(new TheEnd());
            list.add(new TheRecall());
            list.add(new DeadCane());
            list.add(new SingerMask());
            list.add(new BraveBlade());
            list.add(new TenRings());
            list.add(new KnownRelic());
            list.add(new LittleCube());
            list.add(new LiteratureBegins());
            list.add(new BabelWord());
            list.add(new EyeSpy());
            list.add(new NamelessTotem());
            list.add(new WhiteCrown());
            list.add(new UntoldFragments());
            list.add(new GoDudeNoWord());
            list.add(new QuiZaTueStaWord());
            list.add(new BaMissUsWord());
            list.add(new IreShyWord());

            if(bossRelic.isEmpty()){
                for(AbstractRelic r : list){
                    if(r.tier == AbstractRelic.RelicTier.BOSS){
                        bossRelic.add(r.relicId);
                    }
                }
            }
        }
        else{

        }
        return list;
    }

    private static void initializeStart(){
        for(int i = 0; i < 4;i++){
            startDeck.add(Strike.ID);
        }
        for(int i = 0; i < 4;i++){
            startDeck.add(Defend.ID);
        }
        startDeck.add(Spot.ID);
        startDeck.add(Wish.ID);
        startDeck.add(ADust.ID);

        startRelic.add(BabelRelic.ID);
        startRelic.add(UnknownRelic.ID);
    }

    static {
        initializeStart();
    }
}
