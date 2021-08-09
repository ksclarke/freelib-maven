package info.freelibrary.maven.test;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;
import java.net.URI;
import info.freelibrary.util.StringUtils;
import info.freelibrary.util.FileUtils;
/**
 * BEGIN GENERATED CODE
 */
public enum MediaType {
	/**
	 * Media-type for application/atom+xml
	 */
	APPLICATION_ATOM_PLUS_XML("application/atom+xml", new String[]{"atom"}), /**
																				 * Media-type for application/atomcat+xml
																				 */
	APPLICATION_ATOMCAT_PLUS_XML("application/atomcat+xml",
			new String[]{"atomcat"}), /**
										 * Media-type for application/atomsvc+xml
										 */
	APPLICATION_ATOMSVC_PLUS_XML("application/atomsvc+xml",
			new String[]{"atomsvc"}), /**
										 * Media-type for application/cu-seeme
										 */
	APPLICATION_CU_SEEME("application/cu-seeme", new String[]{"cu"}), /**
																		 * Media-type for application/dash+xml
																		 */
	APPLICATION_DASH_PLUS_XML("application/dash+xml", new String[]{"mpd"}), /**
																			 * Media-type for application/dssc+der
																			 */
	APPLICATION_DSSC_PLUS_DER("application/dssc+der", new String[]{"dssc"}), /**
																				 * Media-type for application/dssc+xml
																				 */
	APPLICATION_DSSC_PLUS_XML("application/dssc+xml", new String[]{"xdssc"}), /**
																				 * Media-type for application/ecmascript
																				 */
	APPLICATION_ECMASCRIPT("application/ecmascript", new String[]{"es"}), /**
																			 * Media-type for application/emma+xml
																			 */
	APPLICATION_EMMA_PLUS_XML("application/emma+xml", new String[]{"emma"}), /**
																				 * Media-type for application/epub+zip
																				 */
	APPLICATION_EPUB_PLUS_ZIP("application/epub+zip", new String[]{"epub"}), /**
																				 * Media-type for application/exi
																				 */
	APPLICATION_EXI("application/exi", new String[]{"exi"}), /**
																 * Media-type for application/font-sfnt
																 */
	APPLICATION_FONT_SFNT("application/font-sfnt", new String[]{"otf", "ttf"}), /**
																				 * Media-type for application/gpx+xml
																				 */
	APPLICATION_GPX_PLUS_XML("application/gpx+xml", new String[]{"gpx"}), /**
																			 * Media-type for application/java-archive
																			 */
	APPLICATION_JAVA_ARCHIVE("application/java-archive", new String[]{"jar"}), /**
																				 * Media-type for application/java-vm
																				 */
	APPLICATION_JAVA_VM("application/java-vm", new String[]{"class"}), /**
																		 * Media-type for application/java-serialized-object
																		 */
	APPLICATION_JAVA_SERIALIZED_OBJECT("application/java-serialized-object",
			new String[]{"ser"}), /**
									 * Media-type for application/javascript
									 */
	APPLICATION_JAVASCRIPT("application/javascript", new String[]{"js", "mjs"}), /**
																					 * Media-type for application/json
																					 */
	APPLICATION_JSON("application/json", new String[]{"json"}), /**
																 * Media-type for application/ld+json
																 */
	APPLICATION_LD_PLUS_JSON("application/ld+json", new String[]{"json"}), /**
																			 * Media-type for application/mads+xml
																			 */
	APPLICATION_MADS_PLUS_XML("application/mads+xml", new String[]{"mads"}), /**
																				 * Media-type for application/marc
																				 */
	APPLICATION_MARC("application/marc", new String[]{"marc"}), /**
																 * Media-type for application/marcxml+xml
																 */
	APPLICATION_MARCXML_PLUS_XML("application/marcxml+xml",
			new String[]{"mrcx"}), /**
									 * Media-type for application/mathematica
									 */
	APPLICATION_MATHEMATICA("application/mathematica",
			new String[]{"nb", "nbp"}), /**
										 * Media-type for application/mathml+xml
										 */
	APPLICATION_MATHML_PLUS_XML("application/mathml+xml",
			new String[]{"mathml"}), /**
										 * Media-type for application/mbox
										 */
	APPLICATION_MBOX("application/mbox", new String[]{"mbox"}), /**
																 * Media-type for application/metalink4+xml
																 */
	APPLICATION_METALINK4_PLUS_XML("application/metalink4+xml",
			new String[]{"meta4"}), /**
									 * Media-type for application/mets+xml
									 */
	APPLICATION_METS_PLUS_XML("application/mets+xml", new String[]{"mets"}), /**
																				 * Media-type for application/mods+xml
																				 */
	APPLICATION_MODS_PLUS_XML("application/mods+xml", new String[]{"mods"}), /**
																				 * Media-type for application/mp21
																				 */
	APPLICATION_MP21("application/mp21", new String[]{"m21"}), /**
																 * Media-type for application/msword
																 */
	APPLICATION_MSWORD("application/msword", new String[]{"doc", "dot"}), /**
																			 * Media-type for application/mxf
																			 */
	APPLICATION_MXF("application/mxf", new String[]{"mxf"}), /**
																 * Media-type for application/octet-stream
																 */
	APPLICATION_OCTET_STREAM("application/octet-stream", new String[]{"bin",
			"deploy", "msu", "msp"}), /**
										 * Media-type for application/oebps-package+xml
										 */
	APPLICATION_OEBPS_PACKAGE_PLUS_XML("application/oebps-package+xml",
			new String[]{"opf"}), /**
									 * Media-type for application/ogg
									 */
	APPLICATION_OGG("application/ogg", new String[]{"ogx"}), /**
																 * Media-type for application/onenote
																 */
	APPLICATION_ONENOTE("application/onenote", new String[]{"one", "onetoc2",
			"onetmp", "onepkg"}), /**
									 * Media-type for application/patch-ops-error+xml
									 */
	APPLICATION_PATCH_OPS_ERROR_PLUS_XML("application/patch-ops-error+xml",
			new String[]{"xer"}), /**
									 * Media-type for application/pdf
									 */
	APPLICATION_PDF("application/pdf", new String[]{"pdf"}), /**
																 * Media-type for application/postscript
																 */
	APPLICATION_POSTSCRIPT("application/postscript", new String[]{"ps", "ai",
			"eps", "epsi", "epsf", "eps2", "eps3"}), /**
														 * Media-type for application/prs.cww
														 */
	APPLICATION_PRS_CWW("application/prs.cww", new String[]{"cww"}), /**
																		 * Media-type for application/resource-lists+xml
																		 */
	APPLICATION_RESOURCE_LISTS_PLUS_XML("application/resource-lists+xml",
			new String[]{"rl"}), /**
									 * Media-type for application/rdf+xml
									 */
	APPLICATION_RDF_PLUS_XML("application/rdf+xml", new String[]{"rdf"}), /**
																			 * Media-type for application/relax-ng-compact-syntax
																			 */
	APPLICATION_RELAX_NG_COMPACT_SYNTAX("application/relax-ng-compact-syntax",
			new String[]{"rnc"}), /**
									 * Media-type for application/resource-lists-diff+xml
									 */
	APPLICATION_RESOURCE_LISTS_DIFF_PLUS_XML(
			"application/resource-lists-diff+xml", new String[]{"rld"}), /**
																			 * Media-type for application/rls-services+xml
																			 */
	APPLICATION_RLS_SERVICES_PLUS_XML("application/rls-services+xml",
			new String[]{"rs"}), /**
									 * Media-type for application/rss+xml
									 */
	APPLICATION_RSS_PLUS_XML("application/rss+xml", new String[]{"rss"}), /**
																			 * Media-type for application/rtf
																			 */
	APPLICATION_RTF("application/rtf", new String[]{"rtf"}), /**
																 * Media-type for application/sbml+xml
																 */
	APPLICATION_SBML_PLUS_XML("application/sbml+xml", new String[]{"sbml"}), /**
																				 * Media-type for application/schema+json
																				 */
	APPLICATION_SCHEMA_PLUS_JSON("application/schema+json", new String[]{
			"schema.json", "json"}), /**
										 * Media-type for application/smil+xml
										 */
	APPLICATION_SMIL_PLUS_XML("application/smil+xml", new String[]{"smi",
			"smil"}), /**
						 * Media-type for application/sparql-query
						 */
	APPLICATION_SPARQL_QUERY("application/sparql-query", new String[]{"rq"}), /**
																				 * Media-type for application/sparql-results+xml
																				 */
	APPLICATION_SPARQL_RESULTS_PLUS_XML("application/sparql-results+xml",
			new String[]{"srx"}), /**
									 * Media-type for application/srgs
									 */
	APPLICATION_SRGS("application/srgs", new String[]{"gram"}), /**
																 * Media-type for application/srgs+xml
																 */
	APPLICATION_SRGS_PLUS_XML("application/srgs+xml", new String[]{"grxml"}), /**
																				 * Media-type for application/sru+xml
																				 */
	APPLICATION_SRU_PLUS_XML("application/sru+xml", new String[]{"sru"}), /**
																			 * Media-type for application/ssml+xml
																			 */
	APPLICATION_SSML_PLUS_XML("application/ssml+xml", new String[]{"ssml"}), /**
																				 * Media-type for application/tei+xml
																				 */
	APPLICATION_TEI_PLUS_XML("application/tei+xml", new String[]{"tei"}), /**
																			 * Media-type for application/vnd.3m.post-it-notes
																			 */
	APPLICATION_VND_3M_POST_IT_NOTES("application/vnd.3m.post-it-notes",
			new String[]{"pwn"}), /**
									 * Media-type for application/vnd.adobe.fxp
									 */
	APPLICATION_VND_ADOBE_FXP("application/vnd.adobe.fxp", new String[]{"fxp"}), /**
																					 * Media-type for application/vnd.adobe.xdp+xml
																					 */
	APPLICATION_VND_ADOBE_XDP_PLUS_XML("application/vnd.adobe.xdp+xml",
			new String[]{"xdp"}), /**
									 * Media-type for application/vnd.adobe.xfdf
									 */
	APPLICATION_VND_ADOBE_XFDF("application/vnd.adobe.xfdf",
			new String[]{"xfdf"}), /**
									 * Media-type for application/vnd.amazon.ebook
									 */
	APPLICATION_VND_AMAZON_EBOOK("application/vnd.amazon.ebook",
			new String[]{"azw"}), /**
									 * Media-type for application/vnd.android.package-archive
									 */
	APPLICATION_VND_ANDROID_PACKAGE_ARCHIVE(
			"application/vnd.android.package-archive", new String[]{"apk"}), /**
																				 * Media-type for application/vnd.apple.mpegurl
																				 */
	APPLICATION_VND_APPLE_MPEGURL("application/vnd.apple.mpegurl",
			new String[]{"m3u8"}), /**
									 * Media-type for application/vnd.audiograph
									 */
	APPLICATION_VND_AUDIOGRAPH("application/vnd.audiograph",
			new String[]{"aep"}), /**
									 * Media-type for application/vnd.bmi
									 */
	APPLICATION_VND_BMI("application/vnd.bmi", new String[]{"bmi"}), /**
																		 * Media-type for application/vnd.chemdraw+xml
																		 */
	APPLICATION_VND_CHEMDRAW_PLUS_XML("application/vnd.chemdraw+xml",
			new String[]{"cdxml"}), /**
									 * Media-type for application/vnd.cinderella
									 */
	APPLICATION_VND_CINDERELLA("application/vnd.cinderella",
			new String[]{"cdy"}), /**
									 * Media-type for application/vnd.cups-ppd
									 */
	APPLICATION_VND_CUPS_PPD("application/vnd.cups-ppd", new String[]{"ppd"}), /**
																				 * Media-type for application/vnd.data-vision.rdz
																				 */
	APPLICATION_VND_DATA_VISION_RDZ("application/vnd.data-vision.rdz",
			new String[]{"rdz"}), /**
									 * Media-type for application/vnd.debian.binary-package
									 */
	APPLICATION_VND_DEBIAN_BINARY_PACKAGE(
			"application/vnd.debian.binary-package", new String[]{"deb",
					"ddeb", "udeb"}), /**
										 * Media-type for application/vnd.dolby.mlp
										 */
	APPLICATION_VND_DOLBY_MLP("application/vnd.dolby.mlp", new String[]{"mlp"}), /**
																					 * Media-type for application/vnd.dpgraph
																					 */
	APPLICATION_VND_DPGRAPH("application/vnd.dpgraph", new String[]{"dpg"}), /**
																				 * Media-type for application/vnd.dreamfactory
																				 */
	APPLICATION_VND_DREAMFACTORY("application/vnd.dreamfactory",
			new String[]{"dfac"}), /**
									 * Media-type for application/vnd.dvb.ait
									 */
	APPLICATION_VND_DVB_AIT("application/vnd.dvb.ait", new String[]{"ait"}), /**
																				 * Media-type for application/vnd.dvb.service
																				 */
	APPLICATION_VND_DVB_SERVICE("application/vnd.dvb.service",
			new String[]{"svc"}), /**
									 * Media-type for application/vnd.dynageo
									 */
	APPLICATION_VND_DYNAGEO("application/vnd.dynageo", new String[]{"geo"}), /**
																				 * Media-type for application/vnd.fdf
																				 */
	APPLICATION_VND_FDF("application/vnd.fdf", new String[]{"fdf"}), /**
																		 * Media-type for application/vnd.fdsn.seed
																		 */
	APPLICATION_VND_FDSN_SEED("application/vnd.fdsn.seed", new String[]{"seed"}), /**
																					 * Media-type for application/vnd.framemaker
																					 */
	APPLICATION_VND_FRAMEMAKER("application/vnd.framemaker", new String[]{"fm"}), /**
																					 * Media-type for application/vnd.hal+xml
																					 */
	APPLICATION_VND_HAL_PLUS_XML("application/vnd.hal+xml", new String[]{"hal"}), /**
																					 * Media-type for application/vnd.ibm.rights-management
																					 */
	APPLICATION_VND_IBM_RIGHTS_MANAGEMENT(
			"application/vnd.ibm.rights-management", new String[]{"irm"}), /**
																			 * Media-type for application/vnd.joost.joda-archive
																			 */
	APPLICATION_VND_JOOST_JODA_ARCHIVE("application/vnd.joost.joda-archive",
			new String[]{"joda"}), /**
									 * Media-type for application/vnd.geogebra.file
									 */
	APPLICATION_VND_GEOGEBRA_FILE("application/vnd.geogebra.file",
			new String[]{"ggb"}), /**
									 * Media-type for application/vnd.geogebra.tool
									 */
	APPLICATION_VND_GEOGEBRA_TOOL("application/vnd.geogebra.tool",
			new String[]{"ggt"}), /**
									 * Media-type for application/vnd.geoplan
									 */
	APPLICATION_VND_GEOPLAN("application/vnd.geoplan", new String[]{"g2w"}), /**
																				 * Media-type for application/vnd.geospace
																				 */
	APPLICATION_VND_GEOSPACE("application/vnd.geospace", new String[]{"g3w"}), /**
																				 * Media-type for application/vnd.google-earth.kml+xml
																				 */
	APPLICATION_VND_GOOGLE_EARTH_KML_PLUS_XML(
			"application/vnd.google-earth.kml+xml", new String[]{"kml"}), /**
																			 * Media-type for application/vnd.google-earth.kmz
																			 */
	APPLICATION_VND_GOOGLE_EARTH_KMZ("application/vnd.google-earth.kmz",
			new String[]{"kmz"}), /**
									 * Media-type for application/vnd.grafeq
									 */
	APPLICATION_VND_GRAFEQ("application/vnd.grafeq", new String[]{"gqf"}), /**
																			 * Media-type for application/vnd.iccprofile
																			 */
	APPLICATION_VND_ICCPROFILE("application/vnd.iccprofile",
			new String[]{"icc"}), /**
									 * Media-type for application/vnd.intergeo
									 */
	APPLICATION_VND_INTERGEO("application/vnd.intergeo", new String[]{"i2g"}), /**
																				 * Media-type for application/vnd.kahootz
																				 */
	APPLICATION_VND_KAHOOTZ("application/vnd.kahootz", new String[]{"ktz"}), /**
																				 * Media-type for application/vnd.lotus-1-2-3
																				 */
	APPLICATION_VND_LOTUS_1_2_3("application/vnd.lotus-1-2-3",
			new String[]{"123"}), /**
									 * Media-type for application/vnd.mif
									 */
	APPLICATION_VND_MIF("application/vnd.mif", new String[]{"mif"}), /**
																		 * Media-type for application/vnd.ms-excel
																		 */
	APPLICATION_VND_MS_EXCEL("application/vnd.ms-excel", new String[]{"xls",
			"xlb", "xlt"}), /**
							 * Media-type for application/vnd.ms-powerpoint
							 */
	APPLICATION_VND_MS_POWERPOINT("application/vnd.ms-powerpoint",
			new String[]{"ppt", "pps"}), /**
											 * Media-type for application/vnd.ms-works
											 */
	APPLICATION_VND_MS_WORKS("application/vnd.ms-works", new String[]{"wps"}), /**
																				 * Media-type for application/vnd.ms-wpl
																				 */
	APPLICATION_VND_MS_WPL("application/vnd.ms-wpl", new String[]{"wpl"}), /**
																			 * Media-type for application/vnd.mseq
																			 */
	APPLICATION_VND_MSEQ("application/vnd.mseq", new String[]{"mseq"}), /**
																		 * Media-type for application/vnd.musician
																		 */
	APPLICATION_VND_MUSICIAN("application/vnd.musician", new String[]{"mus"}), /**
																				 * Media-type for application/vnd.oasis.opendocument.chart
																				 */
	APPLICATION_VND_OASIS_OPENDOCUMENT_CHART(
			"application/vnd.oasis.opendocument.chart", new String[]{"odc"}), /**
																				 * Media-type for application/vnd.oasis.opendocument.graphics
																				 */
	APPLICATION_VND_OASIS_OPENDOCUMENT_GRAPHICS(
			"application/vnd.oasis.opendocument.graphics", new String[]{"odg"}), /**
																					 * Media-type for application/vnd.oasis.opendocument.image
																					 */
	APPLICATION_VND_OASIS_OPENDOCUMENT_IMAGE(
			"application/vnd.oasis.opendocument.image", new String[]{"odi"}), /**
																				 * Media-type for application/vnd.oasis.opendocument.presentation
																				 */
	APPLICATION_VND_OASIS_OPENDOCUMENT_PRESENTATION(
			"application/vnd.oasis.opendocument.presentation",
			new String[]{"odp"}), /**
									 * Media-type for application/vnd.oasis.opendocument.spreadsheet
									 */
	APPLICATION_VND_OASIS_OPENDOCUMENT_SPREADSHEET(
			"application/vnd.oasis.opendocument.spreadsheet",
			new String[]{"ods"}), /**
									 * Media-type for application/vnd.oasis.opendocument.text
									 */
	APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT(
			"application/vnd.oasis.opendocument.text", new String[]{"odt"}), /**
																				 * Media-type for application/vnd.oasis.opendocument.text-web
																				 */
	APPLICATION_VND_OASIS_OPENDOCUMENT_TEXT_WEB(
			"application/vnd.oasis.opendocument.text-web", new String[]{"oth"}), /**
																					 * Media-type for application/vnd.openxmlformats-officedocument.presentationml.presentation
																					 */
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_PRESENTATION(
			"application/vnd.openxmlformats-officedocument.presentationml.presentation",
			new String[]{"pptx"}), /**
									 * Media-type for application/vnd.openxmlformats-officedocument.presentationml.slide
									 */
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_SLIDE(
			"application/vnd.openxmlformats-officedocument.presentationml.slide",
			new String[]{"sldx"}), /**
									 * Media-type for application/vnd.openxmlformats-officedocument.presentationml.slideshow
									 */
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_SLIDESHOW(
			"application/vnd.openxmlformats-officedocument.presentationml.slideshow",
			new String[]{"ppsx"}), /**
									 * Media-type for application/vnd.openxmlformats-officedocument.presentationml.template
									 */
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_PRESENTATIONML_TEMPLATE(
			"application/vnd.openxmlformats-officedocument.presentationml.template",
			new String[]{"potx"}), /**
									 * Media-type for application/vnd.openxmlformats-officedocument.spreadsheetml.sheet
									 */
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_SHEET(
			"application/vnd.openxmlformats-officedocument.spreadsheetml.sheet",
			new String[]{"xlsx"}), /**
									 * Media-type for application/vnd.openxmlformats-officedocument.spreadsheetml.template
									 */
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_SPREADSHEETML_TEMPLATE(
			"application/vnd.openxmlformats-officedocument.spreadsheetml.template",
			new String[]{"xltx"}), /**
									 * Media-type for application/vnd.openxmlformats-officedocument.wordprocessingml.document
									 */
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_DOCUMENT(
			"application/vnd.openxmlformats-officedocument.wordprocessingml.document",
			new String[]{"docx"}), /**
									 * Media-type for application/vnd.openxmlformats-officedocument.wordprocessingml.template
									 */
	APPLICATION_VND_OPENXMLFORMATS_OFFICEDOCUMENT_WORDPROCESSINGML_TEMPLATE(
			"application/vnd.openxmlformats-officedocument.wordprocessingml.template",
			new String[]{"dotx"}), /**
									 * Media-type for application/vnd.osgeo.mapguide.package
									 */
	APPLICATION_VND_OSGEO_MAPGUIDE_PACKAGE(
			"application/vnd.osgeo.mapguide.package", new String[]{"mgp"}), /**
																			 * Media-type for application/vnd.recordare.musicxml
																			 */
	APPLICATION_VND_RECORDARE_MUSICXML("application/vnd.recordare.musicxml",
			new String[]{"mxl"}), /**
									 * Media-type for application/vnd.recordare.musicxml+xml
									 */
	APPLICATION_VND_RECORDARE_MUSICXML_PLUS_XML(
			"application/vnd.recordare.musicxml+xml", new String[]{"musicxml"}), /**
																					 * Media-type for application/vnd.rn-realmedia
																					 */
	APPLICATION_VND_RN_REALMEDIA("application/vnd.rn-realmedia",
			new String[]{"rm"}), /**
									 * Media-type for application/vnd.smaf
									 */
	APPLICATION_VND_SMAF("application/vnd.smaf", new String[]{"mmf"}), /**
																		 * Media-type for application/vnd.stardivision.calc
																		 */
	APPLICATION_VND_STARDIVISION_CALC("application/vnd.stardivision.calc",
			new String[]{"sdc"}), /**
									 * Media-type for application/vnd.stardivision.draw
									 */
	APPLICATION_VND_STARDIVISION_DRAW("application/vnd.stardivision.draw",
			new String[]{"sda"}), /**
									 * Media-type for application/vnd.stardivision.impress
									 */
	APPLICATION_VND_STARDIVISION_IMPRESS(
			"application/vnd.stardivision.impress", new String[]{"sdd"}), /**
																			 * Media-type for application/vnd.stardivision.math
																			 */
	APPLICATION_VND_STARDIVISION_MATH("application/vnd.stardivision.math",
			new String[]{"smf"}), /**
									 * Media-type for application/vnd.stardivision.writer
									 */
	APPLICATION_VND_STARDIVISION_WRITER("application/vnd.stardivision.writer",
			new String[]{"sdw"}), /**
									 * Media-type for application/vnd.stardivision.writer-global
									 */
	APPLICATION_VND_STARDIVISION_WRITER_GLOBAL(
			"application/vnd.stardivision.writer-global", new String[]{"sgl"}), /**
																				 * Media-type for application/vnd.sun.xml.calc
																				 */
	APPLICATION_VND_SUN_XML_CALC("application/vnd.sun.xml.calc",
			new String[]{"sxc"}), /**
									 * Media-type for application/vnd.sun.xml.calc.template
									 */
	APPLICATION_VND_SUN_XML_CALC_TEMPLATE(
			"application/vnd.sun.xml.calc.template", new String[]{"stc"}), /**
																			 * Media-type for application/vnd.sun.xml.draw
																			 */
	APPLICATION_VND_SUN_XML_DRAW("application/vnd.sun.xml.draw",
			new String[]{"sxd"}), /**
									 * Media-type for application/vnd.sun.xml.draw.template
									 */
	APPLICATION_VND_SUN_XML_DRAW_TEMPLATE(
			"application/vnd.sun.xml.draw.template", new String[]{"std"}), /**
																			 * Media-type for application/vnd.sun.xml.impress
																			 */
	APPLICATION_VND_SUN_XML_IMPRESS("application/vnd.sun.xml.impress",
			new String[]{"sxi"}), /**
									 * Media-type for application/vnd.sun.xml.impress.template
									 */
	APPLICATION_VND_SUN_XML_IMPRESS_TEMPLATE(
			"application/vnd.sun.xml.impress.template", new String[]{"sti"}), /**
																				 * Media-type for application/vnd.sun.xml.math
																				 */
	APPLICATION_VND_SUN_XML_MATH("application/vnd.sun.xml.math",
			new String[]{"sxm"}), /**
									 * Media-type for application/vnd.sun.xml.writer
									 */
	APPLICATION_VND_SUN_XML_WRITER("application/vnd.sun.xml.writer",
			new String[]{"sxw"}), /**
									 * Media-type for application/vnd.sun.xml.writer.global
									 */
	APPLICATION_VND_SUN_XML_WRITER_GLOBAL(
			"application/vnd.sun.xml.writer.global", new String[]{"sxg"}), /**
																			 * Media-type for application/vnd.sun.xml.writer.template
																			 */
	APPLICATION_VND_SUN_XML_WRITER_TEMPLATE(
			"application/vnd.sun.xml.writer.template", new String[]{"stw"}), /**
																				 * Media-type for application/vnd.tcpdump.pcap
																				 */
	APPLICATION_VND_TCPDUMP_PCAP("application/vnd.tcpdump.pcap", new String[]{
			"cap", "pcap"}), /**
								 * Media-type for application/vnd.unity
								 */
	APPLICATION_VND_UNITY("application/vnd.unity", new String[]{"unityweb"}), /**
																				 * Media-type for application/vnd.uoml+xml
																				 */
	APPLICATION_VND_UOML_PLUS_XML("application/vnd.uoml+xml",
			new String[]{"uoml"}), /**
									 * Media-type for application/vnd.visio
									 */
	APPLICATION_VND_VISIO("application/vnd.visio", new String[]{"vsd", "vst",
			"vsw", "vss"}), /**
							 * Media-type for application/vnd.visio2013
							 */
	APPLICATION_VND_VISIO2013("application/vnd.visio2013", new String[]{"vsdx"}), /**
																					 * Media-type for application/vnd.wolfram.player
																					 */
	APPLICATION_VND_WOLFRAM_PLAYER("application/vnd.wolfram.player",
			new String[]{"nbp"}), /**
									 * Media-type for application/vnd.wordperfect
									 */
	APPLICATION_VND_WORDPERFECT("application/vnd.wordperfect",
			new String[]{"wpd"}), /**
									 * Media-type for application/vnd.xfdl
									 */
	APPLICATION_VND_XFDL("application/vnd.xfdl", new String[]{"xfdl"}), /**
																		 * Media-type for application/vnd.yamaha.openscoreformat
																		 */
	APPLICATION_VND_YAMAHA_OPENSCOREFORMAT(
			"application/vnd.yamaha.openscoreformat", new String[]{"osf"}), /**
																			 * Media-type for application/vnd.yamaha.smaf-audio
																			 */
	APPLICATION_VND_YAMAHA_SMAF_AUDIO("application/vnd.yamaha.smaf-audio",
			new String[]{"saf"}), /**
									 * Media-type for application/vnd.yamaha.smaf-phrase
									 */
	APPLICATION_VND_YAMAHA_SMAF_PHRASE("application/vnd.yamaha.smaf-phrase",
			new String[]{"spf"}), /**
									 * Media-type for application/voicexml+xml
									 */
	APPLICATION_VOICEXML_PLUS_XML("application/voicexml+xml",
			new String[]{"vxml"}), /**
									 * Media-type for application/wasm
									 */
	APPLICATION_WASM("application/wasm", new String[]{"wasm"}), /**
																 * Media-type for application/wspolicy+xml
																 */
	APPLICATION_WSPOLICY_PLUS_XML("application/wspolicy+xml",
			new String[]{"wspolicy"}), /**
										 * Media-type for application/x-7z-compressed
										 */
	APPLICATION_X_7Z_COMPRESSED("application/x-7z-compressed",
			new String[]{"7z"}), /**
									 * Media-type for application/x-abiword
									 */
	APPLICATION_X_ABIWORD("application/x-abiword", new String[]{"abw"}), /**
																			 * Media-type for application/x-ace-compressed
																			 */
	APPLICATION_X_ACE_COMPRESSED("application/x-ace-compressed",
			new String[]{"ace"}), /**
									 * Media-type for application/x-apple-diskimage
									 */
	APPLICATION_X_APPLE_DISKIMAGE("application/x-apple-diskimage",
			new String[]{"dmg"}), /**
									 * Media-type for application/x-authorware-bin
									 */
	APPLICATION_X_AUTHORWARE_BIN("application/x-authorware-bin",
			new String[]{"aab"}), /**
									 * Media-type for application/x-authorware-map
									 */
	APPLICATION_X_AUTHORWARE_MAP("application/x-authorware-map",
			new String[]{"aam"}), /**
									 * Media-type for application/x-authorware-seg
									 */
	APPLICATION_X_AUTHORWARE_SEG("application/x-authorware-seg",
			new String[]{"aas"}), /**
									 * Media-type for application/x-bcpio
									 */
	APPLICATION_X_BCPIO("application/x-bcpio", new String[]{"bcpio"}), /**
																		 * Media-type for application/x-bittorrent
																		 */
	APPLICATION_X_BITTORRENT("application/x-bittorrent",
			new String[]{"torrent"}), /**
										 * Media-type for application/x-bzip
										 */
	APPLICATION_X_BZIP("application/x-bzip", new String[]{"bz"}), /**
																	 * Media-type for application/x-bzip2
																	 */
	APPLICATION_X_BZIP2("application/x-bzip2", new String[]{"bz2"}), /**
																		 * Media-type for application/x-cdf
																		 */
	APPLICATION_X_CDF("application/x-cdf", new String[]{"cdf", "cda"}), /**
																		 * Media-type for application/x-chat
																		 */
	APPLICATION_X_CHAT("application/x-chat", new String[]{"chat"}), /**
																	 * Media-type for application/x-cpio
																	 */
	APPLICATION_X_CPIO("application/x-cpio", new String[]{"cpio"}), /**
																	 * Media-type for application/x-csh
																	 */
	APPLICATION_X_CSH("application/x-csh", new String[]{"csh"}), /**
																	 * Media-type for application/x-debian-package
																	 */
	APPLICATION_X_DEBIAN_PACKAGE("application/x-debian-package", new String[]{
			"deb", "udeb"}), /**
								 * Media-type for application/x-director
								 */
	APPLICATION_X_DIRECTOR("application/x-director", new String[]{"dcr", "dir",
			"dxr"}), /**
						 * Media-type for application/x-dtbook+xml
						 */
	APPLICATION_X_DTBOOK_PLUS_XML("application/x-dtbook+xml",
			new String[]{"dtb"}), /**
									 * Media-type for application/x-dtbresource+xml
									 */
	APPLICATION_X_DTBRESOURCE_PLUS_XML("application/x-dtbresource+xml",
			new String[]{"res"}), /**
									 * Media-type for application/x-dvi
									 */
	APPLICATION_X_DVI("application/x-dvi", new String[]{"dvi"}), /**
																	 * Media-type for application/x-font
																	 */
	APPLICATION_X_FONT("application/x-font", new String[]{"pfa", "pfb", "gsf"}), /**
																					 * Media-type for application/x-font-bdf
																					 */
	APPLICATION_X_FONT_BDF("application/x-font-bdf", new String[]{"bdf"}), /**
																			 * Media-type for application/x-font-ghostscript
																			 */
	APPLICATION_X_FONT_GHOSTSCRIPT("application/x-font-ghostscript",
			new String[]{"gsf"}), /**
									 * Media-type for application/x-font-otf
									 */
	APPLICATION_X_FONT_OTF("application/x-font-otf", new String[]{"otf"}), /**
																			 * Media-type for application/x-font-pcf
																			 */
	APPLICATION_X_FONT_PCF("application/x-font-pcf", new String[]{"pcf",
			"pcf.Z"}), /**
						 * Media-type for application/x-font-ttf
						 */
	APPLICATION_X_FONT_TTF("application/x-font-ttf", new String[]{"ttf"}), /**
																			 * Media-type for application/x-font-woff
																			 */
	APPLICATION_X_FONT_WOFF("application/x-font-woff", new String[]{"woff"}), /**
																				 * Media-type for application/x-gnumeric
																				 */
	APPLICATION_X_GNUMERIC("application/x-gnumeric", new String[]{"gnumeric"}), /**
																				 * Media-type for application/x-gtar
																				 */
	APPLICATION_X_GTAR("application/x-gtar", new String[]{"gtar"}), /**
																	 * Media-type for application/x-gtar-compressed
																	 */
	APPLICATION_X_GTAR_COMPRESSED("application/x-gtar-compressed",
			new String[]{"tgz", "taz"}), /**
											 * Media-type for application/x-hdf
											 */
	APPLICATION_X_HDF("application/x-hdf", new String[]{"hdf"}), /**
																	 * Media-type for application/x-internet-signup
																	 */
	APPLICATION_X_INTERNET_SIGNUP("application/x-internet-signup",
			new String[]{"ins", "isp"}), /**
											 * Media-type for application/x-koan
											 */
	APPLICATION_X_KOAN("application/x-koan", new String[]{"skp", "skd", "skt",
			"skm"}), /**
						 * Media-type for application/x-kpresenter
						 */
	APPLICATION_X_KPRESENTER("application/x-kpresenter", new String[]{"kpr",
			"kpt"}), /**
						 * Media-type for application/x-kword
						 */
	APPLICATION_X_KWORD("application/x-kword", new String[]{"kwd", "kwt"}), /**
																			 * Media-type for application/x-latex
																			 */
	APPLICATION_X_LATEX("application/x-latex", new String[]{"latex"}), /**
																		 * Media-type for application/x-maker
																		 */
	APPLICATION_X_MAKER("application/x-maker", new String[]{"frm", "maker",
			"frame", "fm", "fb", "book", "fbdoc"}), /**
													 * Media-type for application/x-mobipocket-ebook
													 */
	APPLICATION_X_MOBIPOCKET_EBOOK("application/x-mobipocket-ebook",
			new String[]{"prc"}), /**
									 * Media-type for application/x-msaccess
									 */
	APPLICATION_X_MSACCESS("application/x-msaccess", new String[]{"mdb"}), /**
																			 * Media-type for application/x-msdos-program
																			 */
	APPLICATION_X_MSDOS_PROGRAM("application/x-msdos-program", new String[]{
			"com", "exe", "bat", "dll"}), /**
											 * Media-type for application/x-msdownload
											 */
	APPLICATION_X_MSDOWNLOAD("application/x-msdownload", new String[]{"exe"}), /**
																				 * Media-type for application/x-python-code
																				 */
	APPLICATION_X_PYTHON_CODE("application/x-python-code", new String[]{"pyc",
			"pyo"}), /**
						 * Media-type for application/x-qgis
						 */
	APPLICATION_X_QGIS("application/x-qgis", new String[]{"qgs", "shp", "shx"}), /**
																					 * Media-type for application/x-rar-compressed
																					 */
	APPLICATION_X_RAR_COMPRESSED("application/x-rar-compressed",
			new String[]{"rar"}), /**
									 * Media-type for application/x-scilab
									 */
	APPLICATION_X_SCILAB("application/x-scilab", new String[]{"sci", "sce"}), /**
																				 * Media-type for application/x-sh
																				 */
	APPLICATION_X_SH("application/x-sh", new String[]{"sh"}), /**
																 * Media-type for application/x-shar
																 */
	APPLICATION_X_SHAR("application/x-shar", new String[]{"shar"}), /**
																	 * Media-type for application/x-shockwave-flash
																	 */
	APPLICATION_X_SHOCKWAVE_FLASH("application/x-shockwave-flash",
			new String[]{"swf", "swfl"}), /**
											 * Media-type for application/x-stuffit
											 */
	APPLICATION_X_STUFFIT("application/x-stuffit", new String[]{"sit"}), /**
																			 * Media-type for application/x-stuffitx
																			 */
	APPLICATION_X_STUFFITX("application/x-stuffitx", new String[]{"sitx"}), /**
																			 * Media-type for application/x-tar
																			 */
	APPLICATION_X_TAR("application/x-tar", new String[]{"tar"}), /**
																	 * Media-type for application/x-tex
																	 */
	APPLICATION_X_TEX("application/x-tex", new String[]{"tex"}), /**
																	 * Media-type for application/x-texinfo
																	 */
	APPLICATION_X_TEXINFO("application/x-texinfo", new String[]{"texinfo",
			"texi"}), /**
						 * Media-type for application/x-trash
						 */
	APPLICATION_X_TRASH("application/x-trash", new String[]{"~", "%", "bak",
			"old", "sik"}), /**
							 * Media-type for application/x-troff
							 */
	APPLICATION_X_TROFF("application/x-troff", new String[]{"t", "tr", "roff"}), /**
																					 * Media-type for application/x-xfig
																					 */
	APPLICATION_X_XFIG("application/x-xfig", new String[]{"fig"}), /**
																	 * Media-type for application/xenc+xml
																	 */
	APPLICATION_XENC_PLUS_XML("application/xenc+xml", new String[]{"xenc"}), /**
																				 * Media-type for application/xhtml+xml
																				 */
	APPLICATION_XHTML_PLUS_XML("application/xhtml+xml", new String[]{"xhtml",
			"xht"}), /**
						 * Media-type for application/xml
						 */
	APPLICATION_XML("application/xml", new String[]{"xml", "xsd"}), /**
																	 * Media-type for application/xml-dtd
																	 */
	APPLICATION_XML_DTD("application/xml-dtd", new String[]{"dtd"}), /**
																		 * Media-type for application/xop+xml
																		 */
	APPLICATION_XOP_PLUS_XML("application/xop+xml", new String[]{"xop"}), /**
																			 * Media-type for application/xslt+xml
																			 */
	APPLICATION_XSLT_PLUS_XML("application/xslt+xml", new String[]{"xsl",
			"xslt"}), /**
						 * Media-type for application/xspf+xml
						 */
	APPLICATION_XSPF_PLUS_XML("application/xspf+xml", new String[]{"xspf"}), /**
																				 * Media-type for application/zip
																				 */
	APPLICATION_ZIP("application/zip", new String[]{"zip"}), /**
																 * Media-type for audio/3gpp
																 */
	AUDIO_3GPP("audio/3gpp", new String[]{"3gpp"}), /**
													 * Media-type for audio/3gp2
													 */
	AUDIO_3GP2("audio/3gp2", new String[]{"3gp2"}), /**
													 * Media-type for audio/3gpp2
													 */
	AUDIO_3GPP2("audio/3gpp2", new String[]{"3gpp2"}), /**
														 * Media-type for audio/basic
														 */
	AUDIO_BASIC("audio/basic", new String[]{"au", "snd"}), /**
															 * Media-type for audio/csound
															 */
	AUDIO_CSOUND("audio/csound", new String[]{"csd", "orc", "sco"}), /**
																		 * Media-type for audio/flac
																		 */
	AUDIO_FLAC("audio/flac", new String[]{"flac"}), /**
													 * Media-type for audio/midi
													 */
	AUDIO_MIDI("audio/midi", new String[]{"mid", "midi", "kar"}), /**
																	 * Media-type for audio/mp4
																	 */
	AUDIO_MP4("audio/mp4", new String[]{"mp4", "mp4a"}), /**
															 * Media-type for audio/mpeg
															 */
	AUDIO_MPEG("audio/mpeg", new String[]{"mp3", "mpga", "mpega", "mp2", "m4a"}), /**
																					 * Media-type for audio/ogg
																					 */
	AUDIO_OGG("audio/ogg", new String[]{"ogg", "oga", "opus", "spx"}), /**
																		 * Media-type for audio/vnd.dece.audio
																		 */
	AUDIO_VND_DECE_AUDIO("audio/vnd.dece.audio", new String[]{"uva"}), /**
																		 * Media-type for audio/vnd.digital-winds
																		 */
	AUDIO_VND_DIGITAL_WINDS("audio/vnd.digital-winds", new String[]{"eol"}), /**
																				 * Media-type for audio/vnd.dra
																				 */
	AUDIO_VND_DRA("audio/vnd.dra", new String[]{"dra"}), /**
															 * Media-type for audio/vnd.dts
															 */
	AUDIO_VND_DTS("audio/vnd.dts", new String[]{"dts"}), /**
															 * Media-type for audio/vnd.dts.hd
															 */
	AUDIO_VND_DTS_HD("audio/vnd.dts.hd", new String[]{"dtshd"}), /**
																	 * Media-type for audio/webm
																	 */
	AUDIO_WEBM("audio/webm", new String[]{"weba"}), /**
													 * Media-type for audio/x-aac
													 */
	AUDIO_X_AAC("audio/x-aac", new String[]{"aac"}), /**
														 * Media-type for audio/x-aiff
														 */
	AUDIO_X_AIFF("audio/x-aiff", new String[]{"aif", "aiff", "aifc"}), /**
																		 * Media-type for audio/x-ms-wax
																		 */
	AUDIO_X_MS_WAX("audio/x-ms-wax", new String[]{"wax"}), /**
															 * Media-type for audio/x-ms-wma
															 */
	AUDIO_X_MS_WMA("audio/x-ms-wma", new String[]{"wma"}), /**
															 * Media-type for audio/x-pn-realaudio
															 */
	AUDIO_X_PN_REALAUDIO("audio/x-pn-realaudio",
			new String[]{"ra", "rm", "ram"}), /**
												 * Media-type for audio/x-wav
												 */
	AUDIO_X_WAV("audio/x-wav", new String[]{"wav"}), /**
														 * Media-type for chemical/x-cache
														 */
	CHEMICAL_X_CACHE("chemical/x-cache", new String[]{"cac", "cache"}), /**
																		 * Media-type for chemical/x-cactvs-binary
																		 */
	CHEMICAL_X_CACTVS_BINARY("chemical/x-cactvs-binary", new String[]{"cbin",
			"cascii", "ctab"}), /**
								 * Media-type for chemical/x-cdx
								 */
	CHEMICAL_X_CDX("chemical/x-cdx", new String[]{"cdx"}), /**
															 * Media-type for chemical/x-cif
															 */
	CHEMICAL_X_CIF("chemical/x-cif", new String[]{"cif"}), /**
															 * Media-type for chemical/x-cmdf
															 */
	CHEMICAL_X_CMDF("chemical/x-cmdf", new String[]{"cmdf"}), /**
																 * Media-type for chemical/x-cml
																 */
	CHEMICAL_X_CML("chemical/x-cml", new String[]{"cml"}), /**
															 * Media-type for chemical/x-csml
															 */
	CHEMICAL_X_CSML("chemical/x-csml", new String[]{"csml", "csm"}), /**
																		 * Media-type for chemical/x-cxf
																		 */
	CHEMICAL_X_CXF("chemical/x-cxf", new String[]{"cxf", "cef"}), /**
																	 * Media-type for chemical/x-embl-dl-nucleotide
																	 */
	CHEMICAL_X_EMBL_DL_NUCLEOTIDE("chemical/x-embl-dl-nucleotide",
			new String[]{"emb", "embl"}), /**
											 * Media-type for chemical/x-gamess-input
											 */
	CHEMICAL_X_GAMESS_INPUT("chemical/x-gamess-input", new String[]{"inp",
			"gam", "gamin"}), /**
								 * Media-type for chemical/x-gaussian-checkpoint
								 */
	CHEMICAL_X_GAUSSIAN_CHECKPOINT("chemical/x-gaussian-checkpoint",
			new String[]{"fch", "fchk"}), /**
											 * Media-type for chemical/x-gaussian-input
											 */
	CHEMICAL_X_GAUSSIAN_INPUT("chemical/x-gaussian-input", new String[]{"gau",
			"gjc", "gjf"}), /**
							 * Media-type for chemical/x-isostar
							 */
	CHEMICAL_X_ISOSTAR("chemical/x-isostar", new String[]{"ist", "istr"}), /**
																			 * Media-type for chemical/x-jcamp-dx
																			 */
	CHEMICAL_X_JCAMP_DX("chemical/x-jcamp-dx", new String[]{"jdx", "dx"}), /**
																			 * Media-type for chemical/x-macromodel-input
																			 */
	CHEMICAL_X_MACROMODEL_INPUT("chemical/x-macromodel-input", new String[]{
			"mmd", "mmod"}), /**
								 * Media-type for chemical/x-mdl-sdfile
								 */
	CHEMICAL_X_MDL_SDFILE("chemical/x-mdl-sdfile", new String[]{"sd", "sdf"}), /**
																				 * Media-type for chemical/x-mopac-input
																				 */
	CHEMICAL_X_MOPAC_INPUT("chemical/x-mopac-input", new String[]{"mop",
			"mopcrt", "mpc", "zmt"}), /**
										 * Media-type for chemical/x-ncbi-asn1-ascii
										 */
	CHEMICAL_X_NCBI_ASN1_ASCII("chemical/x-ncbi-asn1-ascii", new String[]{
			"prt", "ent"}), /**
							 * Media-type for chemical/x-ncbi-asn1-binary
							 */
	CHEMICAL_X_NCBI_ASN1_BINARY("chemical/x-ncbi-asn1-binary", new String[]{
			"val", "aso"}), /**
							 * Media-type for chemical/x-pdb
							 */
	CHEMICAL_X_PDB("chemical/x-pdb", new String[]{"pdb", "ent"}), /**
																	 * Media-type for font/otf
																	 */
	FONT_OTF("font/otf", new String[]{"ttf", "otf"}), /**
														 * Media-type for font/sfnt
														 */
	FONT_SFNT("font/sfnt", new String[]{"ttf", "otf"}), /**
														 * Media-type for font/ttf
														 */
	FONT_TTF("font/ttf", new String[]{"ttf", "otf"}), /**
														 * Media-type for image/bmp
														 */
	IMAGE_BMP("image/bmp", new String[]{"bmp"}), /**
													 * Media-type for image/cgm
													 */
	IMAGE_CGM("image/cgm", new String[]{"cgm"}), /**
													 * Media-type for image/g3fax
													 */
	IMAGE_G3FAX("image/g3fax", new String[]{"g3"}), /**
													 * Media-type for image/gif
													 */
	IMAGE_GIF("image/gif", new String[]{"gif"}), /**
													 * Media-type for image/ief
													 */
	IMAGE_IEF("image/ief", new String[]{"ief"}), /**
													 * Media-type for image/jp2
													 */
	IMAGE_JP2("image/jp2", new String[]{"jp2", "jpg2"}), /**
															 * Media-type for image/jpeg
															 */
	IMAGE_JPEG("image/jpeg", new String[]{"jpg", "jpeg", "jpe"}), /**
																	 * Media-type for image/jpx
																	 */
	IMAGE_JPX("image/jpx", new String[]{"jpx", "jpf"}), /**
														 * Media-type for image/ktx
														 */
	IMAGE_KTX("image/ktx", new String[]{"ktx"}), /**
													 * Media-type for image/pjpeg
													 */
	IMAGE_PJPEG("image/pjpeg", new String[]{"pjpeg"}), /**
														 * Media-type for image/png
														 */
	IMAGE_PNG("image/png", new String[]{"png"}), /**
													 * Media-type for image/svg+xml
													 */
	IMAGE_SVG_PLUS_XML("image/svg+xml", new String[]{"svg", "svgz"}), /**
																		 * Media-type for image/tiff
																		 */
	IMAGE_TIFF("image/tiff", new String[]{"tif", "tiff"}), /**
															 * Media-type for image/vnd.adobe.photoshop
															 */
	IMAGE_VND_ADOBE_PHOTOSHOP("image/vnd.adobe.photoshop", new String[]{"psd"}), /**
																					 * Media-type for image/vnd.dece.graphic
																					 */
	IMAGE_VND_DECE_GRAPHIC("image/vnd.dece.graphic", new String[]{"uvi"}), /**
																			 * Media-type for image/vnd.djvu
																			 */
	IMAGE_VND_DJVU("image/vnd.djvu", new String[]{"djv", "djvu"}), /**
																	 * Media-type for image/vnd.dwg
																	 */
	IMAGE_VND_DWG("image/vnd.dwg", new String[]{"dwg"}), /**
															 * Media-type for image/vnd.dxf
															 */
	IMAGE_VND_DXF("image/vnd.dxf", new String[]{"dxf"}), /**
															 * Media-type for image/vnd.fpx
															 */
	IMAGE_VND_FPX("image/vnd.fpx", new String[]{"fpx"}), /**
															 * Media-type for image/vnd.ms-modi
															 */
	IMAGE_VND_MS_MODI("image/vnd.ms-modi", new String[]{"mdi"}), /**
																	 * Media-type for image/vnd.net-fpx
																	 */
	IMAGE_VND_NET_FPX("image/vnd.net-fpx", new String[]{"npx"}), /**
																	 * Media-type for image/vnd.xiff
																	 */
	IMAGE_VND_XIFF("image/vnd.xiff", new String[]{"xif"}), /**
															 * Media-type for image/webp
															 */
	IMAGE_WEBP("image/webp", new String[]{"webp"}), /**
													 * Media-type for image/x-citrix-jpeg
													 */
	IMAGE_X_CITRIX_JPEG("image/x-citrix-jpeg", new String[]{"jpeg", "jpg"}), /**
																				 * Media-type for image/x-cmu-raster
																				 */
	IMAGE_X_CMU_RASTER("image/x-cmu-raster", new String[]{"ras"}), /**
																	 * Media-type for image/x-cmx
																	 */
	IMAGE_X_CMX("image/x-cmx", new String[]{"cmx"}), /**
														 * Media-type for image/x-freehand
														 */
	IMAGE_X_FREEHAND("image/x-freehand", new String[]{"fh"}), /**
																 * Media-type for image/x-icon
																 */
	IMAGE_X_ICON("image/x-icon", new String[]{"ico"}), /**
														 * Media-type for image/x-pcx
														 */
	IMAGE_X_PCX("image/x-pcx", new String[]{"pcx"}), /**
														 * Media-type for image/x-pict
														 */
	IMAGE_X_PICT("image/x-pict", new String[]{"pic"}), /**
														 * Media-type for image/x-portable-anymap
														 */
	IMAGE_X_PORTABLE_ANYMAP("image/x-portable-anymap", new String[]{"pnm"}), /**
																				 * Media-type for image/x-portable-bitmap
																				 */
	IMAGE_X_PORTABLE_BITMAP("image/x-portable-bitmap", new String[]{"pbm"}), /**
																				 * Media-type for image/x-portable-graymap
																				 */
	IMAGE_X_PORTABLE_GRAYMAP("image/x-portable-graymap", new String[]{"pgm"}), /**
																				 * Media-type for image/x-rgb
																				 */
	IMAGE_X_RGB("image/x-rgb", new String[]{"rgb"}), /**
														 * Media-type for image/x-xbitmap
														 */
	IMAGE_X_XBITMAP("image/x-xbitmap", new String[]{"xbm"}), /**
																 * Media-type for image/x-xpixmap
																 */
	IMAGE_X_XPIXMAP("image/x-xpixmap", new String[]{"xpm"}), /**
																 * Media-type for message/rfc822
																 */
	MESSAGE_RFC822("message/rfc822", new String[]{"eml"}), /**
															 * Media-type for model/iges
															 */
	MODEL_IGES("model/iges", new String[]{"igs", "iges"}), /**
															 * Media-type for model/mesh
															 */
	MODEL_MESH("model/mesh", new String[]{"msh", "mesh", "silo"}), /**
																	 * Media-type for model/vnd.collada+xml
																	 */
	MODEL_VND_COLLADA_PLUS_XML("model/vnd.collada+xml", new String[]{"dae"}), /**
																				 * Media-type for model/vnd.gdl
																				 */
	MODEL_VND_GDL("model/vnd.gdl", new String[]{"gdl"}), /**
															 * Media-type for model/vnd.dwf
															 */
	MODEL_VND_DWF("model/vnd.dwf", new String[]{"dwf"}), /**
															 * Media-type for model/vnd.gtw
															 */
	MODEL_VND_GTW("model/vnd.gtw", new String[]{"gtw"}), /**
															 * Media-type for model/vrml
															 */
	MODEL_VRML("model/vrml", new String[]{"wrl", "vrml"}), /**
															 * Media-type for text/calendar
															 */
	TEXT_CALENDAR("text/calendar", new String[]{"ics", "icz"}), /**
																 * Media-type for text/css
																 */
	TEXT_CSS("text/css", new String[]{"css"}), /**
												 * Media-type for text/csv
												 */
	TEXT_CSV("text/csv", new String[]{"csv"}), /**
												 * Media-type for text/html
												 */
	TEXT_HTML("text/html", new String[]{"html", "htm", "shtml"}), /**
																	 * Media-type for text/markdown
																	 */
	TEXT_MARKDOWN("text/markdown", new String[]{"md", "markdown"}), /**
																	 * Media-type for text/plain
																	 */
	TEXT_PLAIN("text/plain", new String[]{"txt", "asc", "text", "pot", "brf",
			"srt"}), /**
						 * Media-type for text/richtext
						 */
	TEXT_RICHTEXT("text/richtext", new String[]{"rtx"}), /**
															 * Media-type for text/scriptlet
															 */
	TEXT_SCRIPTLET("text/scriptlet", new String[]{"sct", "wsc"}), /**
																	 * Media-type for text/sgml
																	 */
	TEXT_SGML("text/sgml", new String[]{"sgml"}), /**
													 * Media-type for text/tab-separated-values
													 */
	TEXT_TAB_SEPARATED_VALUES("text/tab-separated-values", new String[]{"tsv"}), /**
																					 * Media-type for text/troff
																					 */
	TEXT_TROFF("text/troff", new String[]{"t"}), /**
													 * Media-type for text/turtle
													 */
	TEXT_TURTLE("text/turtle", new String[]{"ttl"}), /**
														 * Media-type for text/uri-list
														 */
	TEXT_URI_LIST("text/uri-list", new String[]{"uri"}), /**
															 * Media-type for text/vcard
															 */
	TEXT_VCARD("text/vcard", new String[]{"vcf", "vcard"}), /**
															 * Media-type for text/vnd.graphviz
															 */
	TEXT_VND_GRAPHVIZ("text/vnd.graphviz", new String[]{"gv"}), /**
																 * Media-type for text/vnd.in3d.3dml
																 */
	TEXT_VND_IN3D_3DML("text/vnd.in3d.3dml", new String[]{"3dml"}), /**
																	 * Media-type for text/vnd.in3d.spot
																	 */
	TEXT_VND_IN3D_SPOT("text/vnd.in3d.spot", new String[]{"spot"}), /**
																	 * Media-type for text/vtt
																	 */
	TEXT_VTT("text/vtt", new String[]{"vtt"}), /**
												 * Media-type for text/x-c++hdr
												 */
	TEXT_X_C_PLUS__PLUS_HDR("text/x-c++hdr", new String[]{"hpp", "h++", "hxx",
			"hh"}), /**
					 * Media-type for text/x-c++src
					 */
	TEXT_X_C_PLUS__PLUS_SRC("text/x-c++src", new String[]{"cpp", "c++", "cxx",
			"cc"}), /**
					 * Media-type for text/x-diff
					 */
	TEXT_X_DIFF("text/x-diff", new String[]{"diff", "patch"}), /**
																 * Media-type for text/x-java-source
																 */
	TEXT_X_JAVA_SOURCE("text/x-java-source", new String[]{"java"}), /**
																	 * Media-type for text/x-pascal
																	 */
	TEXT_X_PASCAL("text/x-pascal", new String[]{"p", "pas"}), /**
																 * Media-type for text/x-perl
																 */
	TEXT_X_PERL("text/x-perl", new String[]{"pl", "pm"}), /**
															 * Media-type for text/x-tcl
															 */
	TEXT_X_TCL("text/x-tcl", new String[]{"tcl", "tk"}), /**
															 * Media-type for text/x-tex
															 */
	TEXT_X_TEX("text/x-tex", new String[]{"tex", "ltx", "sty", "cls"}), /**
																		 * Media-type for text/x-uuencode
																		 */
	TEXT_X_UUENCODE("text/x-uuencode", new String[]{"uu"}), /**
															 * Media-type for text/x-vcalendar
															 */
	TEXT_X_VCALENDAR("text/x-vcalendar", new String[]{"vcs"}), /**
																 * Media-type for text/x-vcard
																 */
	TEXT_X_VCARD("text/x-vcard", new String[]{"vcf"}), /**
														 * Media-type for text/xml
														 */
	TEXT_XML("text/xml", new String[]{"xml"}), /**
												 * Media-type for text/yaml
												 */
	TEXT_YAML("text/yaml", new String[]{"yaml", "yml"}), /**
															 * Media-type for video/3gpp
															 */
	VIDEO_3GPP("video/3gpp", new String[]{"3gp"}), /**
													 * Media-type for video/3gpp2
													 */
	VIDEO_3GPP2("video/3gpp2", new String[]{"3g2"}), /**
														 * Media-type for video/dv
														 */
	VIDEO_DV("video/dv", new String[]{"dif", "dv"}), /**
														 * Media-type for video/h261
														 */
	VIDEO_H261("video/h261", new String[]{"h261"}), /**
													 * Media-type for video/h263
													 */
	VIDEO_H263("video/h263", new String[]{"h263"}), /**
													 * Media-type for video/h264
													 */
	VIDEO_H264("video/h264", new String[]{"h264"}), /**
													 * Media-type for video/jpeg
													 */
	VIDEO_JPEG("video/jpeg", new String[]{"jpgv"}), /**
													 * Media-type for video/jpm
													 */
	VIDEO_JPM("video/jpm", new String[]{"jpm"}), /**
													 * Media-type for video/mj2
													 */
	VIDEO_MJ2("video/mj2", new String[]{"mj2"}), /**
													 * Media-type for video/mp4
													 */
	VIDEO_MP4("video/mp4", new String[]{"mp4"}), /**
													 * Media-type for video/mpeg
													 */
	VIDEO_MPEG("video/mpeg", new String[]{"mpg", "mpeg", "mpe"}), /**
																	 * Media-type for video/ogg
																	 */
	VIDEO_OGG("video/ogg", new String[]{"ogv"}), /**
													 * Media-type for video/quicktime
													 */
	VIDEO_QUICKTIME("video/quicktime", new String[]{"qt", "mov"}), /**
																	 * Media-type for video/vnd.dece.hd
																	 */
	VIDEO_VND_DECE_HD("video/vnd.dece.hd", new String[]{"uvh"}), /**
																	 * Media-type for video/vnd.dece.mobile
																	 */
	VIDEO_VND_DECE_MOBILE("video/vnd.dece.mobile", new String[]{"uvm"}), /**
																			 * Media-type for video/vnd.mpegurl
																			 */
	VIDEO_VND_MPEGURL("video/vnd.mpegurl", new String[]{"mxu"}), /**
																	 * Media-type for video/vnd.uvvu.mp4
																	 */
	VIDEO_VND_UVVU_MP4("video/vnd.uvvu.mp4", new String[]{"uvu"}), /**
																	 * Media-type for video/vnd.dece.pd
																	 */
	VIDEO_VND_DECE_PD("video/vnd.dece.pd", new String[]{"uvp"}), /**
																	 * Media-type for video/vnd.dece.sd
																	 */
	VIDEO_VND_DECE_SD("video/vnd.dece.sd", new String[]{"uvs"}), /**
																	 * Media-type for video/vnd.dece.video
																	 */
	VIDEO_VND_DECE_VIDEO("video/vnd.dece.video", new String[]{"uvv"}), /**
																		 * Media-type for video/webm
																		 */
	VIDEO_WEBM("video/webm", new String[]{"webm"}), /**
													 * Media-type for video/x-f4v
													 */
	VIDEO_X_F4V("video/x-f4v", new String[]{"f4v"}), /**
														 * Media-type for video/x-fli
														 */
	VIDEO_X_FLI("video/x-fli", new String[]{"fli"}), /**
														 * Media-type for video/x-flv
														 */
	VIDEO_X_FLV("video/x-flv", new String[]{"flv"}), /**
														 * Media-type for video/x-la-asf
														 */
	VIDEO_X_LA_ASF("video/x-la-asf", new String[]{"lsf", "lsx"}), /**
																	 * Media-type for video/x-m4v
																	 */
	VIDEO_X_M4V("video/x-m4v", new String[]{"m4v"}), /**
														 * Media-type for video/x-ms-asf
														 */
	VIDEO_X_MS_ASF("video/x-ms-asf", new String[]{"asf", "asx"}), /**
																	 * Media-type for video/x-ms-wm
																	 */
	VIDEO_X_MS_WM("video/x-ms-wm", new String[]{"wm"}), /**
														 * Media-type for video/x-ms-wmv
														 */
	VIDEO_X_MS_WMV("video/x-ms-wmv", new String[]{"wmv"}), /**
															 * Media-type for video/x-ms-wmx
															 */
	VIDEO_X_MS_WMX("video/x-ms-wmx", new String[]{"wmx"}), /**
															 * Media-type for video/x-ms-wvx
															 */
	VIDEO_X_MS_WVX("video/x-ms-wvx", new String[]{"wvx"}), /**
															 * Media-type for video/x-msvideo
															 */
	VIDEO_X_MSVIDEO("video/x-msvideo", new String[]{"avi"}), /**
																 * Media-type for video/x-matroska
																 */
	VIDEO_X_MATROSKA("video/x-matroska", new String[]{"mkv", "mpv"}), /**
																		 * Media-type for video/x-sgi-movie
																		 */
	VIDEO_X_SGI_MOVIE("video/x-sgi-movie", new String[]{"movie"}), /**
																	 * Media-type for x-world/x-vrml
																	 */
	X_WORLD_X_VRML("x-world/x-vrml", new String[]{"vrm", "vrml", "wrl"}), /**
																			 * Media-type for application/cellml+xml
																			 */
	APPLICATION_CELLML_PLUS_XML("application/cellml+xml", new String[]{
			"cellml", "cml"}), /**
								 * Media-type for application/gltf-buffer
								 */
	APPLICATION_GLTF_BUFFER("application/gltf-buffer", new String[]{"glbin",
			"glbuf"}), /**
						 * Media-type for application/inkml+xml
						 */
	APPLICATION_INKML_PLUS_XML("application/inkml+xml", new String[]{"ink",
			"inkml"}), /**
						 * Media-type for application/pgp-keys
						 */
	APPLICATION_PGP_KEYS("application/pgp-keys", new String[]{"asc", "key"}), /**
																				 * Media-type for application/pkcs12
																				 */
	APPLICATION_PKCS12("application/pkcs12", new String[]{"p12", "pfx"}), /**
																			 * Media-type for application/pkcs7-mime
																			 */
	APPLICATION_PKCS7_MIME("application/pkcs7-mime", new String[]{"p7m", "p7c",
			"p7z"}), /**
						 * Media-type for application/prs.nprend
						 */
	APPLICATION_PRS_NPREND("application/prs.nprend", new String[]{"rnd", "rct"}), /**
																					 * Media-type for application/sieve
																					 */
	APPLICATION_SIEVE("application/sieve", new String[]{"siv", "sieve"}), /**
																			 * Media-type for application/vnd.acucorp
																			 */
	APPLICATION_VND_ACUCORP("application/vnd.acucorp", new String[]{"atc",
			"acutc"}), /**
						 * Media-type for application/vnd.afpc.modca
						 */
	APPLICATION_VND_AFPC_MODCA("application/vnd.afpc.modca", new String[]{
			"list3820", "listafp", "afp", "pseg3820"}), /**
														 * Media-type for application/vnd.apple.installer+xml
														 */
	APPLICATION_VND_APPLE_INSTALLER_PLUS_XML(
			"application/vnd.apple.installer+xml", new String[]{"dist",
					"distz", "pkg", "mpkg"}), /**
												 * Media-type for application/vnd.clonk.c4group
												 */
	APPLICATION_VND_CLONK_C4GROUP("application/vnd.clonk.c4group",
			new String[]{"c4g", "c4d", "c4f", "c4p", "c4u"}), /**
																 * Media-type for application/vnd.commerce-battelle
																 */
	APPLICATION_VND_COMMERCE_BATTELLE("application/vnd.commerce-battelle",
			new String[]{"icf", "icd", "ic0", "ic1", "ic2", "ic3", "ic4",
					"ic5", "ic6", "ic7", "ic8"}), /**
													 * Media-type for application/vnd.commonspace
													 */
	APPLICATION_VND_COMMONSPACE("application/vnd.commonspace", new String[]{
			"csp", "cst"}), /**
							 * Media-type for application/vnd.coreos.ignition+json
							 */
	APPLICATION_VND_COREOS_IGNITION_PLUS_JSON(
			"application/vnd.coreos.ignition+json", new String[]{"ign",
					"ignition"}), /**
									 * Media-type for application/vnd.dece.data
									 */
	APPLICATION_VND_DECE_DATA("application/vnd.dece.data", new String[]{"uvf",
			"uvvf", "uvd", "uvvd"}), /**
										 * Media-type for application/vnd.dece.ttml+xml
										 */
	APPLICATION_VND_DECE_TTML_PLUS_XML("application/vnd.dece.ttml+xml",
			new String[]{"uvt", "uvvt"}), /**
											 * Media-type for application/vnd.dece.unspecified
											 */
	APPLICATION_VND_DECE_UNSPECIFIED("application/vnd.dece.unspecified",
			new String[]{"uvx", "uvvx"}), /**
											 * Media-type for application/vnd.dece.zip
											 */
	APPLICATION_VND_DECE_ZIP("application/vnd.dece.zip", new String[]{"uvz",
			"uvvz"}), /**
						 * Media-type for application/vnd.ericsson.quickcall
						 */
	APPLICATION_VND_ERICSSON_QUICKCALL("application/vnd.ericsson.quickcall",
			new String[]{"qcall", "qca"}), /**
											 * Media-type for application/vnd.eszigno3+xml
											 */
	APPLICATION_VND_ESZIGNO3_PLUS_XML("application/vnd.eszigno3+xml",
			new String[]{"es3", "et3"}), /**
											 * Media-type for application/vnd.etsi.asic-e+zip
											 */
	APPLICATION_VND_ETSI_ASIC_E_PLUS_ZIP("application/vnd.etsi.asic-e+zip",
			new String[]{"asice", "sce"}), /**
											 * Media-type for application/vnd.fdsn.mseed
											 */
	APPLICATION_VND_FDSN_MSEED("application/vnd.fdsn.mseed", new String[]{
			"msd", "mseed"}), /**
								 * Media-type for application/vnd.geometry-explorer
								 */
	APPLICATION_VND_GEOMETRY_EXPLORER("application/vnd.geometry-explorer",
			new String[]{"gex", "gre"}), /**
											 * Media-type for application/vnd.hbci
											 */
	APPLICATION_VND_HBCI("application/vnd.hbci", new String[]{"hbci", "hbc",
			"kom", "upa", "pkd", "bpd"}), /**
											 * Media-type for application/vnd.hp-hpid
											 */
	APPLICATION_VND_HP_HPID("application/vnd.hp-hpid", new String[]{"hpi",
			"hpid"}), /**
						 * Media-type for application/vnd.intercon.formnet
						 */
	APPLICATION_VND_INTERCON_FORMNET("application/vnd.intercon.formnet",
			new String[]{"xpw", "xpx"}), /**
											 * Media-type for application/vnd.kde.kpresenter
											 */
	APPLICATION_VND_KDE_KPRESENTER("application/vnd.kde.kpresenter",
			new String[]{"kpr", "kpt"}), /**
											 * Media-type for application/vnd.kde.kword
											 */
	APPLICATION_VND_KDE_KWORD("application/vnd.kde.kword", new String[]{"kwd",
			"kwt"}), /**
						 * Media-type for application/vnd.Kinar
						 */
	APPLICATION_VND_KINAR("application/vnd.Kinar", new String[]{"kne", "knp",
			"sdf"}), /**
						 * Media-type for application/vnd.koan
						 */
	APPLICATION_VND_KOAN("application/vnd.koan", new String[]{"skp", "skd",
			"skm", "skt"}), /**
							 * Media-type for application/vnd.logipipe.circuit+zip
							 */
	APPLICATION_VND_LOGIPIPE_CIRCUIT_PLUS_ZIP(
			"application/vnd.logipipe.circuit+zip", new String[]{"lcs", "lca"}), /**
																					 * Media-type for application/vnd.lotus-approach
																					 */
	APPLICATION_VND_LOTUS_APPROACH("application/vnd.lotus-approach",
			new String[]{"apr", "vew"}), /**
											 * Media-type for application/vnd.lotus-freelance
											 */
	APPLICATION_VND_LOTUS_FREELANCE("application/vnd.lotus-freelance",
			new String[]{"prz", "pre"}), /**
											 * Media-type for application/vnd.lotus-notes
											 */
	APPLICATION_VND_LOTUS_NOTES("application/vnd.lotus-notes", new String[]{
			"nsf", "ntf", "ndl", "ns4", "ns3", "ns2", "nsh", "nsg"}), /**
																		 * Media-type for application/vnd.lotus-organizer
																		 */
	APPLICATION_VND_LOTUS_ORGANIZER("application/vnd.lotus-organizer",
			new String[]{"or3", "or2", "org"}), /**
												 * Media-type for application/vnd.lotus-wordpro
												 */
	APPLICATION_VND_LOTUS_WORDPRO("application/vnd.lotus-wordpro",
			new String[]{"lwp", "sam"}), /**
											 * Media-type for application/vnd.ms-project
											 */
	APPLICATION_VND_MS_PROJECT("application/vnd.ms-project", new String[]{
			"mpp", "mpt"}), /**
							 * Media-type for application/vnd.ms-tnef
							 */
	APPLICATION_VND_MS_TNEF("application/vnd.ms-tnef", new String[]{"tnef",
			"tnf"}), /**
						 * Media-type for application/vnd.nervana
						 */
	APPLICATION_VND_NERVANA("application/vnd.nervana", new String[]{"entity",
			"request", "bkm", "kcm"}), /**
										 * Media-type for application/vnd.nintendo.snes.rom
										 */
	APPLICATION_VND_NINTENDO_SNES_ROM("application/vnd.nintendo.snes.rom",
			new String[]{"sfc", "smc"}), /**
											 * Media-type for application/vnd.palm
											 */
	APPLICATION_VND_PALM("application/vnd.palm", new String[]{"prc", "pdb",
			"pqa", "oprc"}), /**
								 * Media-type for application/vnd.previewsystems.box
								 */
	APPLICATION_VND_PREVIEWSYSTEMS_BOX("application/vnd.previewsystems.box",
			new String[]{"box", "vbox"}), /**
											 * Media-type for application/vnd.Quark.QuarkXPress
											 */
	APPLICATION_VND_QUARK_QUARKXPRESS("application/vnd.Quark.QuarkXPress",
			new String[]{"qxd", "qxt", "qwd", "qwt", "qxl", "qxb"}), /**
																		 * Media-type for application/vnd.quobject-quoxdocument
																		 */
	APPLICATION_VND_QUOBJECT_QUOXDOCUMENT(
			"application/vnd.quobject-quoxdocument", new String[]{"quox",
					"quiz"}), /**
								 * Media-type for application/vnd.scribus
								 */
	APPLICATION_VND_SCRIBUS("application/vnd.scribus", new String[]{"scd",
			"sla", "slaz"}), /**
								 * Media-type for application/vnd.sealed.doc
								 */
	APPLICATION_VND_SEALED_DOC("application/vnd.sealed.doc", new String[]{
			"sdoc", "sdo", "s1w"}), /**
									 * Media-type for application/vnd.sealed.eml
									 */
	APPLICATION_VND_SEALED_EML("application/vnd.sealed.eml", new String[]{
			"seml", "sem"}), /**
								 * Media-type for application/vnd.sealedmedia.softseal.html
								 */
	APPLICATION_VND_SEALEDMEDIA_SOFTSEAL_HTML(
			"application/vnd.sealedmedia.softseal.html", new String[]{"stml",
					"s1h"}), /**
								 * Media-type for application/vnd.sealedmedia.softseal.pdf
								 */
	APPLICATION_VND_SEALEDMEDIA_SOFTSEAL_PDF(
			"application/vnd.sealedmedia.softseal.pdf", new String[]{"spdf",
					"spd", "s1a"}), /**
									 * Media-type for application/vnd.sealed.mht
									 */
	APPLICATION_VND_SEALED_MHT("application/vnd.sealed.mht", new String[]{
			"smht", "smh"}), /**
								 * Media-type for application/vnd.sealed.ppt
								 */
	APPLICATION_VND_SEALED_PPT("application/vnd.sealed.ppt", new String[]{
			"sppt", "s1p"}), /**
								 * Media-type for application/vnd.sealed.xls
								 */
	APPLICATION_VND_SEALED_XLS("application/vnd.sealed.xls", new String[]{
			"sxls", "sxl", "s1e"}), /**
									 * Media-type for application/vnd.SimTech-MindMapper
									 */
	APPLICATION_VND_SIMTECH_MINDMAPPER("application/vnd.SimTech-MindMapper",
			new String[]{"twd", "twds"}), /**
											 * Media-type for application/vnd.snesdev-page-table
											 */
	APPLICATION_VND_SNESDEV_PAGE_TABLE("application/vnd.snesdev-page-table",
			new String[]{"ptrom", "pt"}), /**
											 * Media-type for application/vnd.solent.sdkm+xml
											 */
	APPLICATION_VND_SOLENT_SDKM_PLUS_XML("application/vnd.solent.sdkm+xml",
			new String[]{"sdkm", "sdkd"}), /**
											 * Media-type for application/vnd.sqlite3
											 */
	APPLICATION_VND_SQLITE3("application/vnd.sqlite3", new String[]{"sqlite",
			"sqlite3"}), /**
							 * Media-type for application/vnd.sus-calendar
							 */
	APPLICATION_VND_SUS_CALENDAR("application/vnd.sus-calendar", new String[]{
			"sus", "susp"}), /**
								 * Media-type for application/vnd.tml
								 */
	APPLICATION_VND_TML("application/vnd.tml", new String[]{"vfr", "viaframe"}), /**
																					 * Media-type for application/vnd.ufdl
																					 */
	APPLICATION_VND_UFDL("application/vnd.ufdl", new String[]{"ufdl", "ufd",
			"frm"}), /**
						 * Media-type for application/vnd.uri-map
						 */
	APPLICATION_VND_URI_MAP("application/vnd.uri-map", new String[]{"urim",
			"urimap"}), /**
						 * Media-type for application/vnd.vd-study
						 */
	APPLICATION_VND_VD_STUDY("application/vnd.vd-study", new String[]{"mxi",
			"study-inter", "model-inter"}), /**
											 * Media-type for application/vnd.veryant.thin
											 */
	APPLICATION_VND_VERYANT_THIN("application/vnd.veryant.thin", new String[]{
			"istc", "isws"}), /**
								 * Media-type for application/vnd.zul
								 */
	APPLICATION_VND_ZUL("application/vnd.zul", new String[]{"zir", "zirz"}), /**
																				 * Media-type for application/xv+xml
																				 */
	APPLICATION_XV_PLUS_XML("application/xv+xml", new String[]{"mxml", "xhvml",
			"xvml", "xvm"}), /**
								 * Media-type for audio/aac
								 */
	AUDIO_AAC("audio/aac", new String[]{"adts", "aac", "ass"}), /**
																 * Media-type for audio/AMR
																 */
	AUDIO_AMR("audio/AMR", new String[]{"amr", "AMR"}), /**
														 * Media-type for audio/AMR-WB
														 */
	AUDIO_AMR_WB("audio/AMR-WB", new String[]{"awb", "AWB"}), /**
																 * Media-type for audio/ATRAC3
																 */
	AUDIO_ATRAC3("audio/ATRAC3", new String[]{"at3", "aa3", "omg"}), /**
																		 * Media-type for audio/EVRC-QCP
																		 */
	AUDIO_EVRC_QCP("audio/EVRC-QCP", new String[]{"qcp", "QCP"}), /**
																	 * Media-type for audio/prs.sid
																	 */
	AUDIO_PRS_SID("audio/prs.sid", new String[]{"sid", "psid"}), /**
																	 * Media-type for audio/usac
																	 */
	AUDIO_USAC("audio/usac", new String[]{"loas", "xhe"}), /**
															 * Media-type for audio/vnd.sealedmedia.softseal.mpeg
															 */
	AUDIO_VND_SEALEDMEDIA_SOFTSEAL_MPEG("audio/vnd.sealedmedia.softseal.mpeg",
			new String[]{"smp3", "smp", "s1m"}), /**
													 * Media-type for image/fits
													 */
	IMAGE_FITS("image/fits", new String[]{"fits", "fit", "fts"}), /**
																	 * Media-type for image/jphc
																	 */
	IMAGE_JPHC("image/jphc", new String[]{"jhc", "jphc"}), /**
															 * Media-type for image/jpm
															 */
	IMAGE_JPM("image/jpm", new String[]{"jpm", "jpgm"}), /**
															 * Media-type for image/prs.btif
															 */
	IMAGE_PRS_BTIF("image/prs.btif", new String[]{"btif", "btf"}), /**
																	 * Media-type for image/t38
																	 */
	IMAGE_T38("image/t38", new String[]{"t38", "T38"}), /**
														 * Media-type for image/vnd.globalgraphics.pgb
														 */
	IMAGE_VND_GLOBALGRAPHICS_PGB("image/vnd.globalgraphics.pgb", new String[]{
			"PGB", "pgb"}), /**
							 * Media-type for image/vnd.radiance
							 */
	IMAGE_VND_RADIANCE("image/vnd.radiance",
			new String[]{"hdr", "rgbe", "xyze"}), /**
													 * Media-type for image/vnd.sealedmedia.softseal.gif
													 */
	IMAGE_VND_SEALEDMEDIA_SOFTSEAL_GIF("image/vnd.sealedmedia.softseal.gif",
			new String[]{"sgif", "sgi", "s1g"}), /**
													 * Media-type for image/vnd.sealedmedia.softseal.jpg
													 */
	IMAGE_VND_SEALEDMEDIA_SOFTSEAL_JPG("image/vnd.sealedmedia.softseal.jpg",
			new String[]{"sjpg", "sjp", "s1j"}), /**
													 * Media-type for image/vnd.sealed.png
													 */
	IMAGE_VND_SEALED_PNG("image/vnd.sealed.png", new String[]{"spng", "spn",
			"s1n"}), /**
						 * Media-type for model/vnd.parasolid.transmit.binary
						 */
	MODEL_VND_PARASOLID_TRANSMIT_BINARY("model/vnd.parasolid.transmit.binary",
			new String[]{"x_b", "xmt_bin"}), /**
												 * Media-type for model/vnd.parasolid.transmit.text
												 */
	MODEL_VND_PARASOLID_TRANSMIT_TEXT("model/vnd.parasolid.transmit.text",
			new String[]{"x_t", "xmt_txt"}), /**
												 * Media-type for model/x3d+vrml
												 */
	MODEL_X3D_PLUS_VRML("model/x3d+vrml", new String[]{"x3dv", "x3dvz"}), /**
																			 * Media-type for text/cache-manifest
																			 */
	TEXT_CACHE_MANIFEST("text/cache-manifest", new String[]{"appcache",
			"manifest"}), /**
							 * Media-type for text/dns
							 */
	TEXT_DNS("text/dns", new String[]{"soa", "zone"}), /**
														 * Media-type for text/prs.lines.tag
														 */
	TEXT_PRS_LINES_TAG("text/prs.lines.tag", new String[]{"tag", "dsc"}), /**
																			 * Media-type for video/vnd.dece.mp4
																			 */
	VIDEO_VND_DECE_MP4("video/vnd.dece.mp4", new String[]{"uvu", "uvvu"}), /**
																			 * Media-type for video/vnd.radgamettools.bink
																			 */
	VIDEO_VND_RADGAMETTOOLS_BINK("video/vnd.radgamettools.bink", new String[]{
			"bik", "bk2"}), /**
							 * Media-type for video/vnd.sealedmedia.softseal.mov
							 */
	VIDEO_VND_SEALEDMEDIA_SOFTSEAL_MOV("video/vnd.sealedmedia.softseal.mov",
			new String[]{"smov", "smo", "s1q"}), /**
													 * Media-type for video/vnd.sealed.mpeg1
													 */
	VIDEO_VND_SEALED_MPEG1("video/vnd.sealed.mpeg1",
			new String[]{"smpg", "s11"}), /**
											 * Media-type for video/vnd.sealed.swf
											 */
	VIDEO_VND_SEALED_SWF("video/vnd.sealed.swf", new String[]{"sswf", "ssw"});

	/**
	 * Sets the media type's identifier.
	 */
	private String myType;
	/**
	 * Sets the media type's extensions.
	 */
	private String[] myExts;

	public String toString() {
		return myType;
	}

	public String getExt() {
		return myExts[0];
	}

	public String[] getExts() {
		return myExts;
	}

	/**
	 * Gets a media type from the supplied type.
	 *
	 * @param aType A type of media type
	 * @return The media type that corresponds to the supplied type
	 */
	public static Optional<MediaType> fromString(final String aType) {
		for (final MediaType mediaType : values()) {
			if (mediaType.myType.equalsIgnoreCase(aType)) {
				return Optional.of(mediaType);
			}
		}
		return Optional.empty();
	}

	/**
	 * Gets a media type from the supplied extension.
	 *
	 * @param aExt The extension of the desired media type
	 * @return The media type that corresponds to the supplied extension
	 */
	public static Optional<MediaType> fromExt(final String aExt) {
		return fromExt(aExt, null);
	}

	/**
	 * Gets a media type from the supplied extension.
	 *
	 * @param aExt The extension of the desired media type
	 * @param aHint A class of type (e.g. 'audio' or 'application')
	 * @return The media type that corresponds to the supplied extension
	 */
	public static Optional<MediaType> fromExt(final String aExt,
			final String aHint) {
		final String hint = aHint != null ? aHint.toLowerCase() : null;
		MediaType chosenMediaType = null;
		for (final MediaType mediaType : values()) {
			for (final String ext : mediaType.getExts()) {
				if (ext.equalsIgnoreCase(aExt)) {
					if (hint != null && mediaType.toString().startsWith(hint)) {
						return Optional.of(mediaType);
					}
					if (chosenMediaType == null) {
						chosenMediaType = mediaType;
					}
				}
			}
		}
		return Optional.ofNullable(chosenMediaType);
	}

	/**
	 * Gets a list of media types that correspond to the supplied class.
	 *
	 * @param aClass A class of media type (e.g., &quot;application&quot;)
	 * @return The media types that correspond to the supplied type class
	 */
	public static List<MediaType> getTypes(final String aClass) {
		final List<MediaType> types = new ArrayList<>();
		for (final MediaType mediaType : values()) {
			if (mediaType.myType.startsWith(aClass.toLowerCase() + "/")) {
				types.add(mediaType);
			}
		}
		return types;
	}

	/**
	 * Gets a media type from the supplied URI's extension.
	 *
	 * @param aURI A string URI from which to parse the media type
	 * @return The media type that corresponds to the supplied URI
	 */
	public static Optional<MediaType> parse(final String aURI) {
		return parse(URI.create(aURI), null);
	}

	/**
	 * Gets a media type from the supplied URI's extension.
	 *
	 * @param aURI A string URI from which to parse the media type
	 * @param aHint A class of type (e.g. 'audio' or 'application')
	 * @return The media type that corresponds to the supplied URI
	 */
	public static Optional<MediaType> parse(final String aURI,
			final String aHint) {
		return parse(URI.create(aURI), aHint);
	}

	/**
	 * Gets a media type from the supplied URI's extension.
	 *
	 * @param aURI A URI from which to parse the media type
	 * @return The media type that corresponds to the supplied URI
	 */
	public static Optional<MediaType> parse(final URI aURI) {
		return parse(aURI, null);
	}

	/**
	 * Gets a media type from the supplied URI's extension.
	 *
	 * @param aURI A URI from which to parse the media type
	 * @param aHint A hint as to what class of media type we want
	 * @return The media type that corresponds to the supplied URI
	 */
	public static Optional<MediaType> parse(final URI aURI, final String aHint) {
		final String fragment = '#' + aURI.getFragment();
		final String ext;
		final int index;
		String uri = aURI.toString();
		if ((index = uri.indexOf(fragment)) != -1) {
			uri = uri.substring(0, index);
		}
		ext = StringUtils.trimToNull(FileUtils.getExt(uri));
		if (ext != null) {
			return fromExt(ext, aHint);
		}
		return fromString(uri);
	}

	/**
	 * Creates a new media type.
	 */
	MediaType(final String aType, final String[] aExts) {
		myType = aType;
		myExts = aExts;
	}
}