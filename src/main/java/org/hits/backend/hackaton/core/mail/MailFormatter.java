package org.hits.backend.hackaton.core.mail;

import org.hits.backend.hackaton.public_interface.defect.DefectFullDto;
import org.hits.backend.hackaton.public_interface.statement.StatementFullDto;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MailFormatter {
    public String formatFullStatement(StatementFullDto dto, List<DefectFullDto> defects) {
        var builder = new StringBuilder();

        builder.append("<!DOCTYPE html><html>");
        builder.append("<meta http-equiv=\"Content-Type\" content=\"text/html charset=UTF-8\" />");
        builder.append("<h1>Statement</h1>");
        builder.append("<p>Area name: ").append(dto.areaName()).append("</p>");
        builder.append("<p>Length: ").append(dto.length()).append("</p>");
        builder.append("<p>Road type: ").append(dto.roadType()).append("</p>");
        builder.append("<p>Surface type: ").append(dto.surfaceType()).append("</p>");
        builder.append("<p>Direction: ").append(dto.direction()).append("</p>");
        builder.append("<p>Deadline: ").append(dto.deadline()).append("</p>");
        builder.append("<p>Created at: ").append(dto.createdAt()).append("</p>");
        builder.append("<p>Description: ").append(dto.description()).append("</p>");
        builder.append("<p>Status: ").append(dto.status()).append("</p>");

        builder.append("<h2>Defects</h2>");
        builder.append("<table border=\"1\">");
        builder.append("<tr><th>Lattitude</th><th>Longitude</th><th>Type</th><th>Defect Distance</th><th>Images Before</th><th>Images After</th></tr>");
        defects.forEach(defect -> {
            builder.append("<tr>");
            builder.append("<td>").append(defect.latitude()).append("</td>");
            builder.append("<td>").append(defect.longitude()).append("</td>");
            builder.append("<td>").append(defect.type()).append("</td>");
            builder.append("<td>").append(defect.defectDistance()).append("</td>");
            builder.append("<td>");
            defect.imagesBefore().forEach(image -> builder.append("<img src=\"").append(image).append("\"/>"));
            builder.append("</td>");
            builder.append("<td>");
            defect.imagesAfter().forEach(image -> builder.append("<img src=\"").append(image).append("\"/>"));
            builder.append("</td>");
            builder.append("</tr>");
        });
        builder.append("</table>");
        builder.append("</html>");

        return builder.toString();
    }
}
