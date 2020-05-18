package ch.actifsource.example.refactoring;

import java.util.Calendar;
import java.util.List;
import ch.actifsource.core.CorePackage;
import ch.actifsource.core.INode;
import ch.actifsource.core.Package;
import ch.actifsource.core.Statement;
import ch.actifsource.core.job.Select;
import ch.actifsource.core.job.Update;
import ch.actifsource.core.model.aspects.impl.AbstractRefactorerAspect;
import ch.actifsource.core.update.IModifiable;
import ch.actifsource.core.util.LiteralUtil;
import ch.actifsource.example.refactoring.generic.GenericPackage;

public class TestRefactoring extends AbstractRefactorerAspect {
  
  public TestRefactoring() {
    super("10.10.0", 2020, Calendar.MAY, 18, "Update Name Prefix");
  }

  @Override
  public void refactor(IModifiable modifiable, List<Package> packages) {
    
    for (INode instance :Select.instances(modifiable, GenericPackage.Root)) {
      Statement statement = Select.statementForAttributeOrNull(modifiable, CorePackage.NamedResource_name, instance);
      if (statement == null) continue;
      String value = LiteralUtil.getStringValue(statement.object());
      if (value.startsWith("Prefix_")) continue;
      value = "Prefix_"+value;
      Update.modify(modifiable, statement, LiteralUtil.create(value));
    }
  }
}