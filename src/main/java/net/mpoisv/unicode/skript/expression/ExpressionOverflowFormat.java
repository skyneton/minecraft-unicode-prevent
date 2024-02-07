package net.mpoisv.unicode.skript.expression;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import net.mpoisv.unicode.Main;
import org.bukkit.event.Event;

public class ExpressionOverflowFormat extends SimpleExpression<String> {
    private Expression<String> message;
    @Override
    protected String[] get(Event event) {
        var str = message.getSingle(event);
        if(str == null) return new String[] { null };
        return new String[] { Main.detectOverflowFormat(str) };
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event event, boolean debug) {
        return "message overflow format";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        message = (Expression<String>) expressions[0];
        return true;
    }

    static {
        Skript.registerExpression(ExpressionOverflowFormat.class, String.class, ExpressionType.PROPERTY, "%string% to [unicode( |-)]overflow[( |-)format]", "[unicode( |-)]overflow[( |-)format] of %string%");
    }
}
