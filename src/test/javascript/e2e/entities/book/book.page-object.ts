import { element, by, ElementFinder } from 'protractor';

export class BookComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-book div table .btn-danger'));
  title = element.all(by.css('jhi-book div h2#page-heading span')).first();
  noResult = element(by.id('no-result'));
  entities = element(by.id('entities'));

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class BookUpdatePage {
  pageTitle = element(by.id('jhi-book-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  nameOFBookInput = element(by.id('field_nameOFBook'));
  authorNameInput = element(by.id('field_authorName'));
  nbOfBooksInput = element(by.id('field_nbOfBooks'));
  isDnNomberInput = element(by.id('field_isDnNomber'));
  subjectBookInput = element(by.id('field_subjectBook'));
  langOfBookInput = element(by.id('field_langOfBook'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setNameOFBookInput(nameOFBook: string): Promise<void> {
    await this.nameOFBookInput.sendKeys(nameOFBook);
  }

  async getNameOFBookInput(): Promise<string> {
    return await this.nameOFBookInput.getAttribute('value');
  }

  async setAuthorNameInput(authorName: string): Promise<void> {
    await this.authorNameInput.sendKeys(authorName);
  }

  async getAuthorNameInput(): Promise<string> {
    return await this.authorNameInput.getAttribute('value');
  }

  async setNbOfBooksInput(nbOfBooks: string): Promise<void> {
    await this.nbOfBooksInput.sendKeys(nbOfBooks);
  }

  async getNbOfBooksInput(): Promise<string> {
    return await this.nbOfBooksInput.getAttribute('value');
  }

  async setIsDnNomberInput(isDnNomber: string): Promise<void> {
    await this.isDnNomberInput.sendKeys(isDnNomber);
  }

  async getIsDnNomberInput(): Promise<string> {
    return await this.isDnNomberInput.getAttribute('value');
  }

  async setSubjectBookInput(subjectBook: string): Promise<void> {
    await this.subjectBookInput.sendKeys(subjectBook);
  }

  async getSubjectBookInput(): Promise<string> {
    return await this.subjectBookInput.getAttribute('value');
  }

  async setLangOfBookInput(langOfBook: string): Promise<void> {
    await this.langOfBookInput.sendKeys(langOfBook);
  }

  async getLangOfBookInput(): Promise<string> {
    return await this.langOfBookInput.getAttribute('value');
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class BookDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-book-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-book'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
