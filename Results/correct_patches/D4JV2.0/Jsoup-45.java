void resetInsertionMode() {
    boolean last = false;
    for (int pos = stack.size() -1; pos >= 0; pos--) {
        Element node = stack.get(pos);
        if (pos == 0) {
            last = true;
            node = contextElement;
        }
        String name = node.nodeName();
        if ("select".equals(name)) {
            transition(HtmlTreeBuilderState.InSelect);
            break; // frag
        } else if (("td".equals(name) || "th".equals(name)) && !last) { // Fixed Line
            transition(HtmlTreeBuilderState.InCell);
            break;
        } else if ("tr".equals(name)) {
            transition(HtmlTreeBuilderState.InRow);
            break;
        } else if ("tbody".equals(name) || "thead".equals(name) || "tfoot".equals(name)) {
            transition(HtmlTreeBuilderState.InTableBody);
            break;
        } else if ("caption".equals(name)) {
            transition(HtmlTreeBuilderState.InCaption);
            break;
        } else if ("colgroup".equals(name)) {
            transition(HtmlTreeBuilderState.InColumnGroup);
            break; // frag
        } else if ("table".equals(name)) {
            transition(HtmlTreeBuilderState.InTable);
            break;
        } else if ("head".equals(name)) {
            transition(HtmlTreeBuilderState.InBody);
            break; // frag
        } else if ("body".equals(name)) {
            transition(HtmlTreeBuilderState.InBody);
            break;
        } else if ("frameset".equals(name)) {
            transition(HtmlTreeBuilderState.InFrameset);
            break; // frag
        } else if ("html".equals(name)) {
            transition(HtmlTreeBuilderState.BeforeHead);
            break; // frag
        } else if (last) {
            transition(HtmlTreeBuilderState.InBody);
            break; // frag
        }
    }
}