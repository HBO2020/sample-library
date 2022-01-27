import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { CatalogueComponentsPage, CatalogueDeleteDialog, CatalogueUpdatePage } from './catalogue.page-object';

const expect = chai.expect;

describe('Catalogue e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let catalogueComponentsPage: CatalogueComponentsPage;
  let catalogueUpdatePage: CatalogueUpdatePage;
  let catalogueDeleteDialog: CatalogueDeleteDialog;
  const username = process.env.E2E_USERNAME ?? 'admin';
  const password = process.env.E2E_PASSWORD ?? 'admin';

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing(username, password);
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Catalogues', async () => {
    await navBarPage.goToEntity('catalogue');
    catalogueComponentsPage = new CatalogueComponentsPage();
    await browser.wait(ec.visibilityOf(catalogueComponentsPage.title), 5000);
    expect(await catalogueComponentsPage.getTitle()).to.eq('sampleLibraryApp.catalogue.home.title');
    await browser.wait(ec.or(ec.visibilityOf(catalogueComponentsPage.entities), ec.visibilityOf(catalogueComponentsPage.noResult)), 1000);
  });

  it('should load create Catalogue page', async () => {
    await catalogueComponentsPage.clickOnCreateButton();
    catalogueUpdatePage = new CatalogueUpdatePage();
    expect(await catalogueUpdatePage.getPageTitle()).to.eq('sampleLibraryApp.catalogue.home.createOrEditLabel');
    await catalogueUpdatePage.cancel();
  });

  it('should create and save Catalogues', async () => {
    const nbButtonsBeforeCreate = await catalogueComponentsPage.countDeleteButtons();

    await catalogueComponentsPage.clickOnCreateButton();

    await promise.all([
      catalogueUpdatePage.setNameOfAuthorInput('nameOfAuthor'),
      catalogueUpdatePage.setNbOfCopiesInput('5'),
      // catalogueUpdatePage.bookSelectLastOption(),
    ]);

    await catalogueUpdatePage.save();
    expect(await catalogueUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await catalogueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Catalogue', async () => {
    const nbButtonsBeforeDelete = await catalogueComponentsPage.countDeleteButtons();
    await catalogueComponentsPage.clickOnLastDeleteButton();

    catalogueDeleteDialog = new CatalogueDeleteDialog();
    expect(await catalogueDeleteDialog.getDialogTitle()).to.eq('sampleLibraryApp.catalogue.delete.question');
    await catalogueDeleteDialog.clickOnConfirmButton();
    await browser.wait(ec.visibilityOf(catalogueComponentsPage.title), 5000);

    expect(await catalogueComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
