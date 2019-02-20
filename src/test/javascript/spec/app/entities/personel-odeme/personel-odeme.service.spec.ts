/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { PersonelOdemeService } from 'app/entities/personel-odeme/personel-odeme.service';
import { IPersonelOdeme, PersonelOdeme, OdemeTuru, OdemeYontemi } from 'app/shared/model/personel-odeme.model';

describe('Service Tests', () => {
    describe('PersonelOdeme Service', () => {
        let injector: TestBed;
        let service: PersonelOdemeService;
        let httpMock: HttpTestingController;
        let elemDefault: IPersonelOdeme;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(PersonelOdemeService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new PersonelOdeme(0, currentDate, 0, OdemeTuru.MAAS, OdemeYontemi.NAKIT);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        tarih: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a PersonelOdeme', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        tarih: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        tarih: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new PersonelOdeme(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a PersonelOdeme', async () => {
                const returnedFromService = Object.assign(
                    {
                        tarih: currentDate.format(DATE_FORMAT),
                        miktar: 1,
                        turu: 'BBBBBB',
                        yontem: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        tarih: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of PersonelOdeme', async () => {
                const returnedFromService = Object.assign(
                    {
                        tarih: currentDate.format(DATE_FORMAT),
                        miktar: 1,
                        turu: 'BBBBBB',
                        yontem: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        tarih: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a PersonelOdeme', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
