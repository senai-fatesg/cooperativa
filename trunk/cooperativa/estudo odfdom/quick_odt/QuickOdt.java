/************************************************************************
 *
 * Copyright 2009 J David Eisenberg All rights reserved.
 *
 * Uses ODF Toolkit which is Copyright 2008 Sun Microsystems, Inc.
 * All rights reserved.
 *
 * Use is subject to license terms.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy
 * of the License at http://www.apache.org/licenses/LICENSE-2.0. You can also
 * obtain a copy of the License at http://odftoolkit.org/docs/license.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 ************************************************************************/
import org.odftoolkit.odfdom.doc.OdfTextDocument;

import java.net.URI;

/**
 *
 * @author J David Eisenberg
 */
public class QuickOdt {

    public static void main(String[] args) {
        OdfTextDocument outputDocument;

		try
		{
			outputDocument =
				OdfTextDocument.newTextDocument();
            outputDocument.addText("I'm using the ODFDOM toolkit!");
            
            outputDocument.newParagraph();
            outputDocument.newImage(new URI("images/odf-community.jpg"));
            
            outputDocument.newParagraph("Now...it's your turn!");
 			
            outputDocument.save("quick.odt");
		}
		catch (Exception e)
		{
			System.err.println("Unable to create output file.");
			System.err.println(e.getMessage());
		}
    }
}
