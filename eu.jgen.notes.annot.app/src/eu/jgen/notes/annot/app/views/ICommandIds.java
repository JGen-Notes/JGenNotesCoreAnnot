package eu.jgen.notes.annot.app.views;

/**
 * Interface defining the application's command IDs.
 * Key bindings can be defined for specific commands.
 * To associate an action with a command, use IAction.setActionDefinitionId(commandId).
 *
 * @see org.eclipse.jface.action.IAction#setActionDefinitionId(String)
 */
public interface ICommandIds {

    public static final String CMD_SHOWONE = "eu.jgen.notes.annot.app.command.showone";
    public static final String CMD_SHOWALL = "eu.jgen.notes.annot.app.command.showall";
 
    
}
