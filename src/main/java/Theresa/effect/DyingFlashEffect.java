package Theresa.effect;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class DyingFlashEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private static final TextureAtlas.AtlasRegion img =  new TextureAtlas.AtlasRegion(ImageMaster.loadImage("TheresaResources/img/powers/DyingPower_84.png"),0,0,84,84);
    private boolean playedSound = false;

    public DyingFlashEffect(float x, float y) {
        this.x = x - (float)img.packedWidth / 2.0F;
        this.y = y - (float)img.packedHeight / 2.0F;
        this.startingDuration = 0.5F;
        this.duration = this.startingDuration;
        this.color = Color.WHITE.cpy();
    }

    public void update() {
        if (!this.playedSound) {
            CardCrawlGame.sound.playA("FIRE", -0.5F);
            this.playedSound = true;
        }

        super.update();
    }

    public void render(SpriteBatch sb) {
        sb.setBlendFunction(770, 1);
        sb.setColor(this.color);
        sb.draw(img, this.x, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * MathUtils.random(2.9F, 3.1F), this.scale * MathUtils.random(2.9F, 3.1F), this.rotation);
        sb.draw(img, this.x, this.y, (float)img.packedWidth / 2.0F, (float)img.packedHeight / 2.0F, (float)img.packedWidth, (float)img.packedHeight, this.scale * MathUtils.random(2.9F, 3.1F), this.scale * MathUtils.random(2.9F, 3.1F), this.rotation);
        sb.setBlendFunction(770, 771);
    }

    public void dispose() {
    }
}

