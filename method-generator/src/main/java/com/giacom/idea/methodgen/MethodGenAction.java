package main.java.com.giacom.idea.methodgen;

import org.apache.commons.lang3.text.WordUtils;
import org.jetbrains.annotations.NotNull;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Caret;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

public class MethodGenAction extends AnAction {

    public MethodGenAction() {
        super("Generate Method...");
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent event) {
        Project project = event.getData(PlatformDataKeys.PROJECT);
        Editor editor = event.getData(CommonDataKeys.EDITOR);
        String input = Messages.showInputDialog(project, "Phrase:", "Input your phrase for method", Messages.getQuestionIcon());
        Document document = editor.getDocument();
        Caret primaryCaret = editor.getCaretModel().getPrimaryCaret();
        int start = primaryCaret.getSelectionStart();
        int end = primaryCaret.getSelectionEnd();
        String output = generateMethod(input);

        WriteCommandAction.runWriteCommandAction(project, () ->
                document.replaceString(start, end, output)
        );
        primaryCaret.removeSelection();
    }

    private String generateMethod(String text) {
        String capitalizeFully = WordUtils.capitalize(text).replaceAll("\\s+", "");
        String methodName = capitalizeFully.substring(0, 1).toLowerCase() + capitalizeFully.substring(1);
        String output = "\tpublic void " + methodName + "() {\n\n\t}";
        return output;
    }

}
