
'/**


#if ( $classDescriptor.doesHaveMetaInfo( "generateStandardJavaDoc", "true") )

' * JavaBean class of the MOGLiCC JavaBean Group.
' *

#end




#set( $JavaDocLines = $classDescriptor.getAllMetaInfoValuesFor("JavaDocLine") )

#foreach($JavaDocLine in $JavaDocLines)

' * $JavaDocLine

#end


#if ( $classDescriptor.doesHaveAnyMetaInfosWithName( "JavaDocLine" ) )

' *

#end



 
' * @author generated by MOGLiCC
' */
