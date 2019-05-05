package cn.wyb.mybatis.generator;

import static org.mybatis.generator.internal.util.StringUtility.isTrue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import org.mybatis.generator.api.CommentGenerator;
import org.mybatis.generator.api.IntrospectedColumn;
import org.mybatis.generator.api.IntrospectedTable;
import org.mybatis.generator.api.dom.java.CompilationUnit;
import org.mybatis.generator.api.dom.java.Field;
import org.mybatis.generator.api.dom.java.InnerClass;
import org.mybatis.generator.api.dom.java.InnerEnum;
import org.mybatis.generator.api.dom.java.JavaElement;
import org.mybatis.generator.api.dom.java.Method;
import org.mybatis.generator.api.dom.java.Parameter;
import org.mybatis.generator.api.dom.java.TopLevelClass;
import org.mybatis.generator.config.MergeConstants;
import org.mybatis.generator.config.PropertyRegistry;

public class MybatisCommentGenerator implements CommentGenerator {

  private Properties properties;
  private Properties systemPro;
  private boolean suppressDate;
  private boolean suppressAllComments;
  private String currentDateStr;

  public MybatisCommentGenerator() {
    super();
    properties = new Properties();
    systemPro = System.getProperties();
    suppressDate = false;
    suppressAllComments = false;
    currentDateStr = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
  }

  /**
   * Adds properties for this instance from any properties configured in the CommentGenerator
   * configuration.
   * <p>     * This method will be called before any of the other methods.
   *
   * @param properties All properties from the configuration
   */
  @Override
  public void addConfigurationProperties(Properties properties) {
    this.properties.putAll(properties);

    suppressDate = isTrue(properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_DATE));

    suppressAllComments = isTrue(
        properties.getProperty(PropertyRegistry.COMMENT_GENERATOR_SUPPRESS_ALL_COMMENTS));
  }

  /**
   * @param field the field
   * @param introspectedTable the introspected table
   */
  @Override
  public void addFieldComment(Field field, IntrospectedTable introspectedTable,
      IntrospectedColumn introspectedColumn) {
    if (suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    field.addJavaDocLine("/**");
    sb.append(" * ");
    sb.append(introspectedColumn.getRemarks());
    field.addJavaDocLine(sb.toString());

    addJavadocTag(field, false);

    field.addJavaDocLine(" */");
  }

  /**
   * Adds the field comment.
   *
   * @param field the field
   */
  @Override
  public void addFieldComment(Field field, IntrospectedTable introspectedTable) {
    if (suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    field.addJavaDocLine("/**");
    sb.append(" * ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    field.addJavaDocLine(sb.toString());
    field.addJavaDocLine(" */");
  }

  @Override
  public void addModelClassComment(TopLevelClass topLevelClass,
      IntrospectedTable introspectedTable) {
    if (suppressAllComments) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    topLevelClass.addJavaDocLine("/**");
    sb.append(" * ");
    sb.append(introspectedTable.getRemarks());
    sb.append(" ");
    sb.append(introspectedTable.getTableType());
    sb.append(" ");
    sb.append(getDateString());
    topLevelClass.addJavaDocLine(sb.toString());
    topLevelClass.addJavaDocLine(" */");
  }

  /**
   * Adds the inner class comment.
   *
   * @param innerClass the inner class
   */
  @Override
  public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable) {
    if (suppressAllComments) {
      return;
    }
    StringBuilder sb = new StringBuilder();
    innerClass.addJavaDocLine("/**");
    sb.append(" * ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    sb.append(" ");
    sb.append(getDateString());
    innerClass.addJavaDocLine(sb.toString());
    innerClass.addJavaDocLine(" */");
  }

  /**
   * Adds the inner class comment.
   *
   * @param innerClass the inner class
   * @param introspectedTable the introspected table
   */
  @Override
  public void addClassComment(InnerClass innerClass, IntrospectedTable introspectedTable,
      boolean markAsDoNotDelete) {
    if (suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    innerClass.addJavaDocLine("/**");
    sb.append(" * ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    innerClass.addJavaDocLine(sb.toString());

    sb.setLength(0);
    sb.append(" * @author ");
    sb.append(systemPro.getProperty("user.name"));
    sb.append(" ");
    sb.append(currentDateStr);

    addJavadocTag(innerClass, markAsDoNotDelete);

    innerClass.addJavaDocLine(" */");
  }

  /**
   * Adds the enum comment.
   *
   * @param innerEnum the inner enum
   */
  @Override
  public void addEnumComment(InnerEnum innerEnum, IntrospectedTable introspectedTable) {
    if (suppressAllComments) {
      return;
    }

    StringBuilder sb = new StringBuilder();

    innerEnum.addJavaDocLine("/**");
    addJavadocTag(innerEnum, false);
    sb.append(" * ");
    sb.append(introspectedTable.getFullyQualifiedTable());
    innerEnum.addJavaDocLine(sb.toString());
    innerEnum.addJavaDocLine(" */");
  }

  /**
   * Adds the getter comment.
   *
   * @param method the method
   * @param introspectedTable the introspected table
   */
  @Override
  public void addGetterComment(Method method, IntrospectedTable introspectedTable,
      IntrospectedColumn introspectedColumn) {
    if (suppressAllComments) {
      return;
    }

    method.addJavaDocLine("/**");

    StringBuilder sb = new StringBuilder();
    sb.append(" * ");
    sb.append(introspectedColumn.getRemarks());
    method.addJavaDocLine(sb.toString());

    sb.setLength(0);
    sb.append(" * @return ");
    sb.append(introspectedColumn.getActualColumnName());
    sb.append(" ");
    sb.append(introspectedColumn.getRemarks());
    method.addJavaDocLine(sb.toString());

    addJavadocTag(method, false);

    method.addJavaDocLine(" */");
  }

  /**
   * Adds the setter comment.
   *
   * @param method the method
   * @param introspectedTable the introspected table
   */
  @Override
  public void addSetterComment(Method method, IntrospectedTable introspectedTable,
      IntrospectedColumn introspectedColumn) {
    if (suppressAllComments) {
      return;
    }

    method.addJavaDocLine("/**");
    StringBuilder sb = new StringBuilder();
    sb.append(" * ");
    sb.append(introspectedColumn.getRemarks());
    method.addJavaDocLine(sb.toString());

    Parameter parm = method.getParameters().get(0);
    sb.setLength(0);
    sb.append(" * @param ");
    sb.append(parm.getName());
    sb.append(" ");
    sb.append(introspectedColumn.getRemarks());
    method.addJavaDocLine(sb.toString());

    addJavadocTag(method, false);

    method.addJavaDocLine(" */");
  }

  /**
   * Adds the general method comment.
   *
   * @param method the method
   */
  @Override
  public void addGeneralMethodComment(Method method, IntrospectedTable introspectedTable) {
    if (suppressAllComments) {
      return;
    }
    method.addJavaDocLine("/**");
//        addJavadocTag(method, false);
    StringBuilder sb = new StringBuilder();
    sb.append(" * ");
    sb.append(MergeConstants.NEW_ELEMENT_TAG);
    String s = method.getName();
    sb.append(' ');
    sb.append(s);
    method.addJavaDocLine(sb.toString());
    method.addJavaDocLine(" */");
  }

  @Override
  public void addJavaFileComment(CompilationUnit compilationUnit) {

  }

  @Override
  public void addComment(org.mybatis.generator.api.dom.xml.XmlElement xmlElement) {

  }

  @Override
  public void addRootComment(org.mybatis.generator.api.dom.xml.XmlElement xmlElement) {

  }

  protected void addJavadocTag(JavaElement javaElement, boolean markAsDoNotDelete) {
    javaElement.addJavaDocLine(" *");
    StringBuilder sb = new StringBuilder();
    sb.append(" * ");
    sb.append(MergeConstants.NEW_ELEMENT_TAG);
    if (markAsDoNotDelete) {
      sb.append(" do_not_delete_during_merge");
    }
    String s = getDateString();
    if (s != null) {
      sb.append(' ');
      sb.append(s);
    }
    javaElement.addJavaDocLine(sb.toString());
  }

  protected String getDateString() {
    String result = null;
    if (!suppressDate) {
      result = currentDateStr;
    }
    return result;
  }
}