/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DefterTakipTestModule } from '../../../test.module';
import { MudurlukDetailComponent } from 'app/entities/mudurluk/mudurluk-detail.component';
import { Mudurluk } from 'app/shared/model/mudurluk.model';

describe('Component Tests', () => {
    describe('Mudurluk Management Detail Component', () => {
        let comp: MudurlukDetailComponent;
        let fixture: ComponentFixture<MudurlukDetailComponent>;
        const route = ({ data: of({ mudurluk: new Mudurluk(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [DefterTakipTestModule],
                declarations: [MudurlukDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MudurlukDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MudurlukDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mudurluk).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
