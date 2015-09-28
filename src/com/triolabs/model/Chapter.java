package com.triolabs.model;

import java.util.Calendar;

/**
* Chapter es el objeto de capitulo en ella se podra instaciar un nuevo capitulo y extraer sus campos
* @author Triolabs
* @Developer Raul Quintero Esparza
* @Designer Ivan Padilla
* @version 1.0
*/
public class Chapter {
	
	private String nameChapter;
	private String photoChapter;
	private String dateNumberChapter;
	private String dateMonthChapter;
	private String urlChapter;
	private Calendar dateChapter;
	private String id;
	private String likes;
	private String nameProgram;
	
	/** Se crea una instancia de Chapter 
 	 */
	public Chapter(){
		
	}

	/** @return String nameChapter: devueleve el nombre del capitulo
 	 */
	public String getNameChapter() {
		return nameChapter;
	}

	/** @param nameChapter asigna el nombre del capitulo
 	 */
	public void setNameChapter(String nameChapter) {
		this.nameChapter = nameChapter;
	}

	/** @return String photoChapter: devueleve el url de la foto del capitulo
 	 */
	public String getPhotoChapter() {
		return photoChapter;
	}

	/** @param photoChapter asigna el url de la foto del capitulo
 	 */
	public void setPhotoChapter(String photoChapter) {
		this.photoChapter = photoChapter;
	}
	
	/** @return String dateNumberChapter: devueleve el dia del mes del capitulo
 	 */
	public String getDateNumberChapter() {
		return dateNumberChapter;
	}

	/** @param dateNumberChapter asigna el dia del mes del capitulo
 	 */
	public void setDateNumberChapter(String dateNumberChapter) {
		this.dateNumberChapter = dateNumberChapter;
	}

	/** @return String dateMonthChapter: devueleve el mes del capitulo
 	 */
	public String getDateMonthChapter() {
		return dateMonthChapter;
	}

	/** @param dateMonthChapter asigna el mes del capitulo
 	 */
	public void setDateMonthChapter(String dateMonthChapter) {
		this.dateMonthChapter = dateMonthChapter;
	}

	/** @return String urlChapter: devueleve el url del video del capitulo
 	 */
	public String getUrlChapter() {
		return urlChapter;
	}

	/** @param urlChapter asigna el url del video del capitulo
 	 */
	public void setUrlChapter(String urlChapter) {
		this.urlChapter = urlChapter;
	}

	/** @return String id: devueleve el id del capitulo
 	 */
	public String getId() {
		return id;
	}

	/** @param id asigna el id del capitulo
 	 */
	public void setId(String id) {
		this.id = id;
	}

	/** @return Calendar dateChapter: devueleve la fecha del capitulo
 	 */
	public Calendar getDateChapter() {
		return dateChapter;
	}

	/** @param dateChapter asigna la fecha del capitulo
 	 */
	public void setDateChapter(Calendar dateChapter) {
		this.dateChapter = dateChapter;
	}

	/** @return String likes: devueleve los likes del capitulo
 	 */
	public String getLikes() {
		return likes;
	}

	/** @param likes asigna los likes del capitulo
 	 */
	public void setLikes(String likes) {
		this.likes = likes;
	}

	/** @return String nameProgram: devueleve el nombre del programa que pertenece el capitulo
 	 */
	public String getNameProgram() {
		return nameProgram;
	}

	/** @param nameProgram asigna el nombre del programa que pertenece el capitulo
 	 */
	public void setNameProgram(String nameProgram) {
		this.nameProgram = nameProgram;
	}

	
}
