package com.opensource.alafama.fama;

import com.opensource.alafama.fama.model.FAMAItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FAMATableParser
{
    private static final boolean LOGGING = true;
    private static final int PUSAT_COUNT = 15;
    private static final int FAMA_ID_MINLENGTH = 2;
    private static final String tagTime = " ns";
    private static long mLogTime = 0;

    public static HashMap<String, List<FAMAItem>> parseHTML(String htmlBody) throws NoTableDataException, NoRowDataException
    {
        final HashMap<String, List<FAMAItem>> outMap = new HashMap<>(PUSAT_COUNT);

        logStart();
        final Element body = Jsoup.parse(htmlBody).body();
        logTime("Parse HTML");

        final Elements tableKeyIdentifiers = body.getElementsContainingOwnText("Pusat");

        if (tableKeyIdentifiers.isEmpty())
            throw new NoTableDataException("Key identified have empty result"); //fast exit

        for (Element tableIdentifier : tableKeyIdentifiers)
        {
            //just for final validation pre insert
            boolean successKey = false;
            logStart();
            //first, get top table parent for "pusat"
            final Element parentNode = findFirstParentWithTag(tableIdentifier, "table");
            logTime("Find TableParent ");

            if (parentNode != null)
            {
                logStart();
                //then, get earliest sibling for that "pusat" table
                final Element sibling = findEarliestSiblingWithTag(parentNode, "table");
                logTime("Find TableSibling");

                if (sibling != null)
                {
                    logStart();
                    final Elements rows = sibling.getElementsByTag("tr");
                    if (rows.isEmpty())
                        throw new NoRowDataException("No rows can be identified"); //fast exit

                    final List<FAMAItem> famaItems = new ArrayList<>();
                    logTime("Find rows");

                    logStart();
                    for (final Element row : rows)
                    {
                        //Only product row have ids
                        if (row.id() != null && row.id().length() > FAMA_ID_MINLENGTH)
                        {
                            final FAMAItem item = parseRow(row);
                            if (item != null)
                            {
                                famaItems.add(item);
                            }
                        }
                    }
                    logTime("Parse rows");

                    if (famaItems.isEmpty()) throw new NoRowDataException("Output rows are empty");

                    successKey = true;
                    outMap.put(tableIdentifier.text(), famaItems);
                }
            }

            if (!successKey)
            {
                //Todo revalidate "TABLE"
            }
        }

        if (outMap.size() == 0) throw new NoTableDataException("No tabular map can be outputted");
        return outMap;
    }

    private static Element findFirstParentWithTag(final Element currentElement, final String tag)
    {
        final Element parent = currentElement.parent();
        if (parent != null && parent.tagName() != null)
        {
            if (parent.tagName().equals("body"))
                return null; //fast exit

            if (parent.tagName().equals(tag))
            {
                return parent;
            }
            else return findFirstParentWithTag(parent, tag);
        }
        return null;
    }

    private static Element findEarliestSiblingWithTag(final Element currentElement, final String tag)
    {
        //Don't use loop! Collection returned by jsoup may not be ordered hence breaking the logic of getting the first matching sibling!
        final Element sibling = currentElement.nextElementSibling();

        if (sibling != null)
        {
            if (sibling.tagName().equals(tag))
            {
                return sibling;
            }
            else
                return findEarliestSiblingWithTag(sibling, tag);
        }
        return null;
    }

    private static FAMAItem parseRow(final Element row)
    {

        if (row != null)
        {
            final FAMAItem famaItem = new FAMAItem(row.id(),
                    row.child(FAMAItem.RowData.NAME.getValue()).text(),
                    row.child(FAMAItem.RowData.GRADE.getValue()).text(),
                    row.child(FAMAItem.RowData.UNIT.getValue()).text(),
                    row.child(FAMAItem.RowData.MAX.getValue()).text(),
                    row.child(FAMAItem.RowData.AVERAGE.getValue()).text(),
                    row.child(FAMAItem.RowData.MIN.getValue()).text());

            if (famaItem.isValid())
            {
                return famaItem;
            }
        }
        return null;
    }

    private static void logStart()
    {
        if (LOGGING)
            mLogTime = System.nanoTime();
    }

    private static void logTime(final String msg)
    {
        if (LOGGING)
        {
            final long nanoTime = System.nanoTime();
            final long displayTime = nanoTime - mLogTime;
            System.out.println(msg + " " + displayTime + tagTime);
        }
    }

    private static void log(String msg)
    {
        if (LOGGING)
            System.out.println(msg);
    }

    public static class NoTableDataException extends IOException
    {
        public NoTableDataException(String detailMessage)
        {
            super(detailMessage);
        }
    }

    public static class NoRowDataException extends IOException
    {
        public NoRowDataException(String detailMessage)
        {
            super(detailMessage);
        }
    }

}
