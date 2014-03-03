#set( $targetDir = $model.getMetaInfoValueFor("targetDir") )
#set( $packagePath = $TemplateStringUtility.replaceAllIn(${classDescriptor.package}, ".", "/") )
#set( $classDescriptor = $model.getClassDescriptor("HelloWorldGreeting") )

@TargetFileName ${classDescriptor.simpleName}.java 
@TargetDir $targetDir/hello_world_simple_example/src/$packagePath
@NameOfValidModel Hello_World_Example_Simple
@CreateNew true

package ${classDescriptor.package};
'
/**
	#set( $comment = ${classDescriptor.getMetaInfoValueFor("comment")} ) 
	' * $comment
' * @author autogenerated by MOGLiCC
' */
public class ${classDescriptor.simpleName} 
{
'	public static void main(String[] args) {

		#set ( $attributeDescriptor = $classDescriptor.getAttributeDescriptor("greeting") )
		#set( $toPrint = ${attributeDescriptor.getMetaInfoValueFor("toPrint")} ) 
		'		System.out.println("${toPrint.trim()}");
		
'	}
}








