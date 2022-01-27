import { element, by, ElementFinder } from 'protractor';

export class CatalogueComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-catalogue div table .btn-danger'));
  title = element.all(by.css('jhi-catalogue div h2#page-heading span')).first();
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

export class CatalogueUpdatePage {
  pageTitle = element(by.id('jhi-catalogue-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  idInput = element(by.id('field_id'));
  nameOfAuthorInput = element(by.id('field_nameOfAuthor'));
  nbOfCopiesInput = element(by.id('field_nbOfCopies'));

  bookSelect = element(by.id('field_book'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setIdInput(id: string): Promise<void> {
    await this.idInput.sendKeys(id);
  }

  async getIdInput(): Promise<string> {
    return await this.idInput.getAttribute('value');
  }

  async setNameOfAuthorInput(nameOfAuthor: string): Promise<void> {
    await this.nameOfAuthorInput.sendKeys(nameOfAuthor);
  }

  async getNameOfAuthorInput(): Promise<string> {
    return await this.nameOfAuthorInput.getAttribute('value');
  }

  async setNbOfCopiesInput(nbOfCopies: string): Promise<void> {
    await this.nbOfCopiesInput.sendKeys(nbOfCopies);
  }

  async getNbOfCopiesInput(): Promise<string> {
    return await this.nbOfCopiesInput.getAttribute('value');
  }

  async bookSelectLastOption(): Promise<void> {
    await this.bookSelect.all(by.tagName('option')).last().click();
  }

  async bookSelectOption(option: string): Promise<void> {
    await this.bookSelect.sendKeys(option);
  }

  getBookSelect(): ElementFinder {
    return this.bookSelect;
  }

  async getBookSelectedOption(): Promise<string> {
    return await this.bookSelect.element(by.css('option:checked')).getText();
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

export class CatalogueDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-catalogue-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-catalogue'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
