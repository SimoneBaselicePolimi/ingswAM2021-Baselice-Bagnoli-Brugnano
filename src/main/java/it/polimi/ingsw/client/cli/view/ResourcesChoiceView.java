package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.UserChoicesUtils;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.client.cli.view.grid.LineBorderStyle;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.gameitems.ResourceType;
import it.polimi.ingsw.utils.Colour;

import java.util.*;
import java.util.function.Consumer;

public class ResourcesChoiceView extends CliView {

    public final int numberOfResourcesToChoose;
    public final Set<ResourceType> possibleChoices;

    protected final String title;

    protected int numberOfResourcesLeftToChoose;
    protected Map<ResourceType, Integer> alreadyChosenResources;

    GridView outerContainer, innerContainer;

    LabelView titleLabel, leftLabel, rightLabel;
    GridView leftLabelBorder, rightLabelBorder;

    protected Consumer<Map<ResourceType, Integer>> onAllChoicesDone = s -> {};

    public ResourcesChoiceView(
        int numberOfResourcesToChoose,
        Set<ResourceType> possibleChoices,
        String title,
        Consumer<Map<ResourceType, Integer>> onAllChoicesDone,
        CliClientManager clientManager,
        int rowSize,
        int columnSize
    ) {
        super(clientManager, rowSize, columnSize);
        this.numberOfResourcesToChoose = numberOfResourcesToChoose;
        this.possibleChoices = possibleChoices;
        this.title = title;
        this.onAllChoicesDone = onAllChoicesDone;

        numberOfResourcesLeftToChoose = numberOfResourcesToChoose;
        alreadyChosenResources = new HashMap<>();

        outerContainer = new GridView(clientManager, 2, 1, 1, rowSize, columnSize);
        outerContainer.setRowWeight(1, 3);
        addChildView(outerContainer, 0, 0);

        titleLabel = new LabelView(FormattedChar.convertStringToFormattedCharList(""), clientManager);
        outerContainer.setView(0, 0, titleLabel);

        innerContainer = new GridView(clientManager, 1, 2, 3);
        outerContainer.setView(1, 0, innerContainer);

        leftLabelBorder = new GridView(clientManager, 1, 1, 1);
        leftLabelBorder.setBorderStyle(new LineBorderStyle());
        innerContainer.setView(0, 0, leftLabelBorder);

        leftLabel = new LabelView(new ArrayList<>(), clientManager);
        leftLabelBorder.setView(0, 0, leftLabel);

        rightLabelBorder = new GridView(clientManager, 1, 1, 1);
        rightLabelBorder.setBorderStyle(new LineBorderStyle());
        innerContainer.setView(0, 1, rightLabelBorder);

        rightLabel = new LabelView(new ArrayList<>(), clientManager);
        rightLabelBorder.setView(0, 0, rightLabel);

        if(numberOfResourcesLeftToChoose == 0)
            onAllChoicesDone.accept(alreadyChosenResources);
        else
            startDialog();

    }

    @Override
    public void setRowSize(int rowSize) {
        outerContainer.setRowSize(rowSize);
        super.setRowSize(rowSize);
    }

    @Override
    public void setColumnSize(int columnSize) {
        outerContainer.setColumnSize(columnSize);
        super.setColumnSize(columnSize);
    }

    public ResourcesChoiceView(
        int numberOfResourcesToChoose,
        Set<ResourceType> possibleChoices,
        CliClientManager clientManager,
        String title,
        Consumer<Map<ResourceType, Integer>> onAllChoicesDone
    ) {
        this(numberOfResourcesToChoose, possibleChoices, title, onAllChoicesDone, clientManager, 0, 0);
    }

    public void addNewChoice(ResourceType resourceType) {
       numberOfResourcesLeftToChoose--;
       alreadyChosenResources.compute(resourceType, (t, v) -> v == null ? 1 : v+1);
       updateView();
       if(numberOfResourcesLeftToChoose == 0)
           onAllChoicesDone.accept(alreadyChosenResources);
    }

    @Override
    public FormattedCharsBuffer getContentAsFormattedCharsBuffer() {

        List<FormattedChar> titleText = FormattedChar.convertStringToFormattedCharList(
            title + ": ",
            Colour.WHITE,
            Colour.BLACK,
            true,
            false,
            false
        );

        String localizedText;
        if (numberOfResourcesLeftToChoose != 1) {
            localizedText = Localization.getLocalizationInstance().getString(
                "client.cli.resourcesChoice.info.plural",
                numberOfResourcesLeftToChoose
            );
        } else {
            localizedText = Localization.getLocalizationInstance().getString(
                "client.cli.resourcesChoice.info.singular"
            );
        }

        List<FormattedChar> resourcesToChooseInfo = FormattedChar.convertStringToFormattedCharList(
            localizedText,
            Colour.WHITE,
            Colour.BLACK,
            false,
            true,
            false
        );

        titleText.addAll(resourcesToChooseInfo);

        titleLabel.setText(titleText);

        leftLabel.setText(FormattedChar.convertStringToFormattedCharList(
            Localization.getLocalizationInstance().getString(
                "client.cli.resourcesChoice.conversions",
                possibleChoices.stream()
                    .map(ResourceType::getLocalizedNameSingular)
                    .reduce("", (a, t) -> a + "\n" + "- " + t)
            )
        ));

        rightLabel.setText(FormattedChar.convertStringToFormattedCharList(
            Localization.getLocalizationInstance().getString("client.cli.resourcesChoice.obtained") +
                "\n" + LocalizationUtils.getResourcesListAsString(alreadyChosenResources)
        ));

        return super.getContentAsFormattedCharsBuffer();
    }

    protected void startDialog() {

        if(possibleChoices.size() > 1) {

            UserChoicesUtils.PossibleUserChoices userChoices = UserChoicesUtils.makeUserChoose(clientManager);

            for (ResourceType t : possibleChoices)
                userChoices.addUserChoiceLocalized(
                    () -> {
                        addNewChoice(t);
                        if (numberOfResourcesLeftToChoose > 0)
                            startDialog();
                    },
                    "client.cli.resourcesChoice.choiceMenu",
                    t.getLocalizedNameSingular()
                );

            userChoices.apply();

        } else {
            onAllChoicesDone.accept(Map.of(possibleChoices.iterator().next(), numberOfResourcesToChoose));
        }

    }

}
