package com.craftersconquest.object.skill;

import com.craftersconquest.object.skill.type.Type;
import com.craftersconquest.object.skill.type.TypeFactory;

public class SkillFactory {

    private final TypeFactory typeFactory;

    public SkillFactory() {
        typeFactory = new TypeFactory();
    }

    public Skill getSkill(String name, double xp, int level) {
        Type type = typeFactory.getType(name);

        return new Skill(type, xp, level);
    }

}
