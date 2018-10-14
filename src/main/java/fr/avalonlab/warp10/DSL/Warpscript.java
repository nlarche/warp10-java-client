package fr.avalonlab.warp10.DSL;

import fr.avalonlab.warp10.DSL.framework.Framework;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Warpscript {

  public static final String SWAP = "SWAP";
  public static final String NOW = "NOW";

  private static final String NEW_LINE = "\n";

  private String rawQuery;
  private String token;
  private Framework[] functions;

  private Warpscript() {
  }

  private Warpscript(String rawQuery) {
    this.rawQuery = rawQuery;
  }

  public static Warpscript builder() {
    return new Warpscript();
  }

  public Warpscript rawQuery(String query) {
    rawQuery = query;
    return this;
  }

  public Warpscript TOKEN(String readToken) {
    this.token = readToken;
    return this;
  }

  public Warpscript functions(Framework... functions) {
    this.functions = functions;
    return this;
  }


  public String formatScript() {
    if (rawQuery != null) {
      return rawQuery;
    }

    String script =  "'" + token + "' " + "'TOKEN' STORE" + NEW_LINE;

    if(functions.length > 0) {
      script += Stream.of(functions).map(Framework::formatScript).collect(Collectors.joining(NEW_LINE));
    }

    return script;
  }

}