package com.poisoniks.chatgod.util;

import net.minecraft.util.StatCollector;

public class AchievementHelper {
    public static String getAchievementName(String achievementKey) {
        return StatCollector.translateToLocal("achievement." + achievementKey);
    }

    public static String getAchievementDescription(String achievementKey) {
        return StatCollector.translateToLocal("achievement." + achievementKey + ".desc");
    }
}
