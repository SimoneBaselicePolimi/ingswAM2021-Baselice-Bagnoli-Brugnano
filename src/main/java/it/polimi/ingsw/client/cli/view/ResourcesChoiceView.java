package it.polimi.ingsw.client.cli.view;

import it.polimi.ingsw.client.cli.CliClientManager;
import it.polimi.ingsw.client.cli.graphicutils.FormattedChar;
import it.polimi.ingsw.client.cli.graphicutils.FormattedCharsBuffer;
import it.polimi.ingsw.client.cli.view.grid.GridView;
import it.polimi.ingsw.localization.Localization;
import it.polimi.ingsw.localization.LocalizationUtils;
import it.polimi.ingsw.server.model.gameitems.ResourceType;

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

    protected Consumer<Map<ResourceType, Integer>> onAllChoicesDone = s -> {};

    public ResourcesChoiceView(
        int numberOfResourcesToChoose,
        Set<ResourceType> possibleChoices,
        String title,
        CliClientManager clientManager,
        int rowSize,
        int columnSize
    ) {
        super(clientManager, rowSize, columnSize);
        this.numberOfResourcesToChoose = numberOfResourcesToChoose;
        this.possibleChoices = possibleChoices;
        this.title = title;

        numberOfResourcesLeftToChoose = numberOfResourcesToChoose;
        alreadyChosenResources = new HashMap<>();

        outerContainer = new GridView(clientManager, 2, 1, 1, rowSize, columnSize);
        outerContainer.setRowWeight(1, 3);
        addChildView(outerContainer, 0, 0);


        titleLabel = new LabelView(FormattedChar.convertStringToFormattedCharList(""), clientManager);
        outerContainer.setView(0, 0, titleLabel);

        innerContainer = new GridView(clientManager, 1, 2, 1);
        outerContainer.setView(1, 0, innerContainer);

        leftLabel = new LabelView(new ArrayList<>(), clientManager);
        innerContainer.setView(0, 0, leftLabel);

        rightLabel = new LabelView(new ArrayList<>(), clientManager);
        innerContainer.setView(0, 1, rightLabel);

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
        String title
    ) {
        this(numberOfResourcesToChoose, possibleChoices, title, clientManager, 0, 0);
    }

    public void addNewChoice(ResourceType resourceType) {
       numberOfResourcesLeftToChoose--;
       alreadyChosenResources.compute(resourceType, (t, v) -> v == null ? 1 : v+1);
       updateView();
       if(numberOfResourcesLeftToChoose == 0)
           onAllChoicesDone.accept(alreadyChosenResources);
    }

    public void setOnAllChoicesDoneCallback(Consumer<Map<ResourceType, Integer>> onAllChoicesDone) {
        this.onAllChoicesDone = onAllChoicesDone;
    }

    @Override
    protected FormattedCharsBuffer buildMyBuffer() {

        List<FormattedChar> titleText = FormattedChar.convertStringToFormattedCharList("");

        String leftText = new StringBuilder().append(
            numberOfResourcesLeftToChoose == 1 ?
                Localization.getLocalizationInstance().getString("client.cli.resourcesChoice.info.singular")
            :
                Localization.getLocalizationInstance().getString(
                    "client.cli.resourcesChoice.info.plural",
                    numberOfResourcesLeftToChoose
                )
        ).append(
            "\n"
        ).append(
            Localization.getLocalizationInstance().getString(
                "client.cli.resourcesChoice.conversions",
                possibleChoices.stream()
                    .map(ResourceType::getLocalizedNameSingular)
                    .reduce("", (a, t) -> a.equals("") ? t : a + ", " + t)
            )
        ).toString();

        leftLabel.setText(FormattedChar.convertStringToFormattedCharList(leftText));

        rightLabel.setText(FormattedChar.convertStringToFormattedCharList(
            Localization.getLocalizationInstance().getString("client.cli.resourcesChoice.obtained") +
                "\n" + LocalizationUtils.getResourcesListAsString(alreadyChosenResources)
        ));

        return super.buildMyBuffer();
    }

    protected void startDialog() {

    }

}
