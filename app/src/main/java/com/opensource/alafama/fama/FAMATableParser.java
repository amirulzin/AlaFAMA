package com.opensource.alafama.fama;

import com.opensource.alafama.fama.model.FAMAItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FAMATableParser
{
    private static final int PUSAT_COUNT = 15;

    public static HashMap<String, List<FAMAItem>> parseHTML(String htmlBody) throws NoTableDataException, NoRowDataException
    {
        final HashMap<String, List<FAMAItem>> outMap = new HashMap<>(PUSAT_COUNT);

        final Element body = Jsoup.parse(htmlBody).body();
        final Elements tableKeyIdentifiers = body.getElementsContainingText("Pusat");

        if (tableKeyIdentifiers.isEmpty()) throw new NoTableDataException(); //fast exit

        for (Element tableIdentifier : tableKeyIdentifiers)
        {
            //just for final validation pre insert
            boolean successKey = false;

            //first, get top table parent for "pusat"
            final Element parentNode = findFirstParentWithTag(tableIdentifier, "table");
            if (parentNode != null)
            {
                //then, get earliest sibling for that "pusat" table
                final Element sibling = findEarliestSiblingWithTag(parentNode, "table");
                if (sibling != null)
                {
                    final Elements rows = sibling.getElementsByTag("tr");
                    if (rows.isEmpty()) throw new NoRowDataException(); //fast exit

                    final List<FAMAItem> famaItems = new ArrayList<>();
                    for (Element row : rows)
                    {
                        //Only product row have ids
                        if(row.id() != null)
                        {
                            final FAMAItem item = parseRow(row);
                            if (item != null)
                            {
                                famaItems.add(item);
                            }
                        }
                    }

                    if (famaItems.isEmpty()) throw new NoRowDataException();

                    successKey = true;
                    outMap.put(tableIdentifier.text(), famaItems);
                }
            }

            if (!successKey)
            {
                //Todo revalidate "TABLE"
            }
        }

        if (outMap.size() == 0) throw new NoTableDataException();
        return outMap;
    }

    private static class NoTableDataException extends Exception {}

    private static class NoRowDataException extends Exception {}

    private static Element findFirstParentWithTag(final Element currentElement, final String tag)
    {
        final Element parent = currentElement.parent();
        if (parent.tagName() != null)
        {
            if (parent.tagName().equals("body"))
                return null; //fast exit

            if (parent.tagName().equals(tag))
            {
                return parent;
            }
            else findFirstParentWithTag(parent, tag);
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

    private static FAMAItem parseRow(Element row)
    {

        if (row != null)
        {
            FAMAItem famaItem = new FAMAItem(row.id(),
                    row.child(FAMAItem.RowData.NAME.getValue()).text(),
                    row.child(FAMAItem.RowData.GRADE.getValue()).text(),
                    row.child(FAMAItem.RowData.UNIT.getValue()).text(),
                    row.child(FAMAItem.RowData.MAX.getValue()).text(),
                    row.child(FAMAItem.RowData.AVERAGE.getValue()).text(),
                    row.child(FAMAItem.RowData.MIN.getValue()).text());

            if(famaItem.isValid()){
                return famaItem;
            }
        }
        return null;
    }

}
