package net.mpoisv.unicode.skript.condition;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.doc.Name;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.mpoisv.unicode.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Name("String is unicode overflow.")
@Description("Check if string overflowed.")
@Examples("if message is unicode overflow:\n\tsend \"overflow\"")
public class ConditionDetectOverflow extends Condition {
    private Expression<String> message;
    private boolean negate;

    @Override
    public boolean check(Event event) {
        var str = message.getSingle(event);
        if(str != null) {
            return negate != Main.detectOverflowUnicodeLength(str);
        }
        return negate;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "message is overflow";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.negate = matchedPattern == 1;
        message = (Expression<String>) expressions[0];
        return true;
    }

    static {
        Skript.registerCondition(ConditionDetectOverflow.class, "%string% is [unicode( |-)]overflow", "%string% is(n't| not) [unicode( |-)]overflow");
    }
}
