protected StringBuffer renderWrappedText(StringBuffer sb, int width, 
                                         int nextLineTabStop, String text)
{
    int pos = findWrapPos(text, width, 0);

    if (pos == -1)
    {
        sb.append(rtrim(text));
        return sb;
    }

    sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);

    if (nextLineTabStop >= width)
    {
        // adjust nextLineTabStop instead of throwing exception
        nextLineTabStop = width - 1;
    }

    final String padding = createPadding(nextLineTabStop);

    while (true)
    {
        text = padding + text.substring(pos).trim();
        pos = findWrapPos(text, width, 0);

        if (pos == -1)
        {
            sb.append(text);
            return sb;
        }
        
        if ((text.length() > width) && (pos == nextLineTabStop - 1))
        {
            pos = width;
        }

        sb.append(rtrim(text.substring(0, pos))).append(defaultNewLine);
    }
}