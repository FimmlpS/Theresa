package Theresa.orb;

import basemod.abstracts.CustomEnergyOrb;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;

public class TheresaEnergyOrb extends CustomEnergyOrb {
    public TheresaEnergyOrb(String[] orbTexturePaths, String orbVfxPath, float[] layerTimers, float[] layerScales) {
        super(orbTexturePaths, orbVfxPath, layerTimers);
        this.layerTimers = layerTimers;
        this.layerScales = layerScales;
        layerYDiffs = new float[this.layerSpeeds.length];
        baseAngle = 0F;
    }

    public static final float Y_A = 15F * Settings.scale;
    public static final float DURATION = 1.5F;
    private static final float ORB_IMG_SCALE = 1.15F * Settings.scale;

    private float[] layerTimers;
    private float[] layerScales;
    private float[] layerYDiffs;
    private float baseAngle;

    @Override
    public void updateOrb(int energyCount) {
        float delta = Gdx.graphics.getDeltaTime();
        if(energyCount == 0){
            delta/=3F;
        }
        for(int i =0;i<layerTimers.length;i++){
            layerTimers[i] += delta;
            if(layerTimers[i] >= 2*DURATION){
                layerTimers[i] -= 2*DURATION;
            }
            double angle = layerTimers[i] / DURATION * Math.PI;
            float diff = (float)(Y_A * Math.sin(angle) * layerScales[i]);
            layerYDiffs[i] = diff;
        }
        baseAngle += delta * 64F;
    }

    @Override
    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y) {
        sb.setColor(Color.WHITE);
        sb.draw(this.baseLayer, current_x - 64.0F, current_y - 64.0F, 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, baseAngle, 0, 0, 128, 128, false, false);

        if (enabled) {
            for(int i = 0; i < this.energyLayers.length; ++i) {
                sb.draw(this.energyLayers[i], current_x - 64.0F, current_y - 64.0F + layerYDiffs[i], 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
            }
        } else {
            for(int i = 0; i < this.noEnergyLayers.length; ++i) {
                sb.draw(this.noEnergyLayers[i], current_x - 64.0F, current_y - 64.0F + layerYDiffs[i], 64.0F, 64.0F, 128.0F, 128.0F, ORB_IMG_SCALE, ORB_IMG_SCALE, 0, 0, 0, 128, 128, false, false);
            }
        }
    }
}
