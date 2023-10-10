package com.tn.query;

import static java.lang.String.join;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import javax.annotation.Nonnull;

import com.tn.query.node.And;
import com.tn.query.node.LogicalNode;
import com.tn.query.node.Node;
import com.tn.query.node.Or;
import com.tn.query.node.Parenthesis;

public class Query
{
  public static final String AND = "&&";
  public static final String OR = "||";
  public static final String PARENTHESIS_CLOSE = ")";
  public static final String PARENTHESIS_OPEN = "(";

  private static final String REGEX_SPLIT_AND = "((?<=(&&))|(?=(&&)))";
  private static final String REGEX_SPLIT_OR = "((?<=(\\|\\|))|(?=(\\|\\|)))";
  private static final String REGEX_SPLIT_PARENTHESIS_OPEN = "((?<=\\()|(?=\\())";
  private static final String REGEX_SPLIT_PARENTHESIS_CLOSE = "((?<=\\))|(?=\\)))";
  private static final String REGEX_LOGICAL_SPLIT = join("|", REGEX_SPLIT_AND, REGEX_SPLIT_OR, REGEX_SPLIT_PARENTHESIS_OPEN, REGEX_SPLIT_PARENTHESIS_CLOSE);

  private Query() {}

  public static Node parse(@Nonnull String s) throws QueryParseException
  {
    return parse(new ArrayList<>(List.of(s.split(REGEX_LOGICAL_SPLIT))), () -> new QueryParseException("Illegal query: " + s));
  }

  private static boolean isNotEmpty(String s)
  {
    return !s.trim().isEmpty();
  }

  private static boolean isAnd(String queryPart)
  {
    return AND.equals(queryPart.trim());
  }

  private static boolean isOr(String queryPart)
  {
    return OR.equals(queryPart.trim());
  }

  private static boolean isCloseParenthesis(String queryPart)
  {
    return PARENTHESIS_CLOSE.equals(queryPart.trim());
  }

  private static boolean isOpenParenthesis(String queryPart)
  {
    return PARENTHESIS_OPEN.equals(queryPart.trim());
  }

  private static Node parse(List<String> queryParts, Supplier<QueryParseException> exceptionSupplier) throws QueryParseException
  {
    Node node = null;

    while (!queryParts.isEmpty())
    {
      String queryPart = queryParts.remove(0);

      if (isNotEmpty(queryPart))
      {
        if (isOpenParenthesis(queryPart))
        {
          node = node(node, new Parenthesis(parse(subQueryParts(queryParts, exceptionSupplier), exceptionSupplier)));
        }
        else if (isAnd(queryPart))
        {
          if (node == null) throw exceptionSupplier.get();
          node = new And(node);
        }
        else if (isOr(queryPart))
        {
          if (node == null) throw exceptionSupplier.get();
          node = new Or(node);
        }
        else
        {
          node = node(node, ComparisonOperator.parseNode(queryPart));
        }
      }
    }

    if (node == null || node.isNotValid()) throw exceptionSupplier.get();

    return node;
  }

  private static Node node(Node currentNode, Node node)
  {
    if (currentNode instanceof LogicalNode)
    {
      return ((LogicalNode<?>)currentNode).withRight(node);
    }
    else
    {
      return node;
    }
  }

  private static List<String> subQueryParts(List<String> queryParts, Supplier<QueryParseException> exceptionSupplier)
  {
    int open = 1;
    List<String> subQueryParts = new ArrayList<>();

    while (!queryParts.isEmpty())
    {
      String queryPart = queryParts.remove(0);

      if (isNotEmpty(queryPart))
      {
        if (isOpenParenthesis(queryPart))
        {
          open++;
        }
        else if (isCloseParenthesis(queryPart))
        {
          open--;
          if (open == 0) return subQueryParts;
        }

        subQueryParts.add(queryPart);
      }
    }

    throw exceptionSupplier.get();
  }
}
