private static void handleRawtext(Token.StartTag startTag, HtmlTreeBuilder tb) {
    tb.tokeniser.transition(TokeniserState.Rawtext);
    tb.markInsertionMode();
    tb.transition(Text);
    tb.insert(startTag);
}